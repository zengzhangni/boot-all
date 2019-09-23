package com.zzn.common.lock;


import com.zzn.common.bean.BeanFactoryUtil;
import com.zzn.common.redis.RedisService;
import com.zzn.common.redis.RedissLockUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Component
@Scope
@Aspect
public class LockAspect {


    @Pointcut("@annotation(com.zzn.common.lock.ServiceLock)")
    public void lockAspect() {
    }


    @Around("lockAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RedisService redisService = BeanFactoryUtil.getBeanByClass(RedisService.class);
        String typeKey = null;
        Long start = null;
        Long end = null;
        Long index = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ServiceLock annotation = method.getAnnotation(ServiceLock.class);

        //获取注解key值
        String key = ElUtil.generateKeyBySpEL(annotation.key(), method, joinPoint.getArgs());
        TypeKey type = annotation.type();
        if (!type.equals(TypeKey.object) && !type.equals(TypeKey.string)) {
            typeKey = ElUtil.generateKeyBySpEL(annotation.typeKey(), method, joinPoint.getArgs());
            start = Long.valueOf(ElUtil.generateKeyBySpEL(annotation.start(), method, joinPoint.getArgs()));
            end = Long.valueOf(ElUtil.generateKeyBySpEL(annotation.end(), method, joinPoint.getArgs()));
            String i = ElUtil.generateKeyBySpEL(annotation.index(), method, joinPoint.getArgs());
            if (StringUtils.isNotBlank(i)) {
                index = Long.valueOf(i);
            }
        }

        // ①redis 有值 从redis返回
        if (exists(key, type, typeKey, redisService, index, start, end)) {
            return get(key, type, typeKey, redisService, start, end, index);
        }

        boolean b = false;
        try {
            //②redis没值 获取锁 从数据库获取
            b = RedissLockUtil.tryLock(key, TimeUnit.SECONDS, 3, 10);
            if (b) {
                //有锁也从redis判断一下,有可能多个线程存在①-②,防止多次调用
                if (exists(key, type, typeKey, redisService, index, start, end)) {
                    return get(key, type, typeKey, redisService, start, end, index);
                }
                //执行方法
                Object proceed = joinPoint.proceed();
                //缓存值 以后请求从redis 获取
                set(key, type, typeKey, proceed, redisService);
                return get(key, type, typeKey, redisService, start, end, index);
            }
            return null;
        } finally {
            if (b) {
                //释放锁
                RedissLockUtil.unlock(key);
            }
        }
    }

    private Object get(String key, TypeKey type, String typeKey, RedisService redisService, Long start, Long end, Long index) {
        switch (type) {
            case object:
                return redisService.get(key);
            case string:
                return redisService.getStr(key);
            case hash:
                return redisService.hashGet(typeKey, key);
            case list:
                if (index != null) {
                    return Arrays.asList(redisService.listGetValue(typeKey, index));
                } else {
                    return redisService.listGetValue(typeKey, start, end);
                }
            case set:
                return redisService.setGetValue(typeKey);
            case zset:
                return redisService.zSetGetValueByIndex(typeKey, start, end);
            default:
                return null;
        }
    }

    private void set(String key, TypeKey type, String typeKey, Object value, RedisService redisService) {
        switch (type) {
            case object:
                if (value == null) {
                    redisService.set(key, null, Long.valueOf(new Random().nextInt(20) + 60));
                } else {
                    redisService.set(key, value);
                }
                break;
            case string:
                if (value == null) {
                    redisService.setStr(key, null, Long.valueOf(new Random().nextInt(20) + 60));
                } else {
                    redisService.setStr(key, value.toString());
                }
                break;
            case hash:
                redisService.hashSet(typeKey, key, value);
                break;
            case list:
                if (value instanceof List) {
                    redisService.listSetAll(typeKey, ((List) value).toArray());
                } else {
                    redisService.listSet(typeKey, value);
                }
                break;
            case set:
                if (value instanceof Set) {
                    redisService.setAddAll(typeKey, ((Set) value).toArray());
                } else {
                    redisService.setAdd(typeKey, value);
                }
                break;
            case zset:
                if (value == null) {
                    redisService.zSetAdd(typeKey, null, -1);
                } else {
                    Set<Map<String, String>> set = (Set) value;
                    set.forEach(map -> {
                        redisService.zSetAdd(typeKey, map.get("value"), Double.valueOf(map.get("score")));
                    });
                }
                break;
            default:

        }

    }

    private Boolean exists(String key, TypeKey type, String typeKey, RedisService redisService, Long index, Long start, Long end) {
        switch (type) {
            case object:
                return redisService.exists(key);
            case string:
                return redisService.existsStr(key);
            case hash:
                return redisService.existsHash(typeKey, key);
            case list:
                if (index != null) {
                    return redisService.existsListByIndex(typeKey, index);
                } else {
                    return redisService.existsList(typeKey, start, end);
                }
            case set:
                return redisService.existsSet(typeKey, key);
            case zset:
                return redisService.existsZSet(typeKey);
            default:
                return false;
        }

    }
}
