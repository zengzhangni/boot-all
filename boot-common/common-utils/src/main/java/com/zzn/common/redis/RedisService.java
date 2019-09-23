package com.zzn.common.redis;

import com.google.gson.Gson;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by GaoWei on 2017/9/28.
 */
@Service
public class RedisService  {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    //----------------------------字符串-------------------------------------------

    /**
     * 写入字符串缓存
     *
     * @param key
     * @param value
     */
    public void setStr(final String key, String value) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(key, value);
    }

    /**
     * 写入字符串缓存 设置过期时间
     *
     * @param key
     * @param value
     */
    public void setStr(final String key, String value, Long expireTime) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(key, value);
        if (expireTime != null) {
            stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取字符串
     *
     * @param key
     * @return
     */
    public String getStr(final String key) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 判断字符串缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean existsStr(final String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 删除字符串缓存
     *
     * @param key
     */
    public void removeStr(final String key) {
        if (existsStr(key)) {
            stringRedisTemplate.delete(key);
        }
    }


    //----------------------------对象-------------------------------------------


    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public void set(final String key, Object value) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
    }


    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public void set(final String key, Object value, Long expireTime) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        if (expireTime != null) {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }


    /**
     * 读取缓存对象
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }


    //----------------------------哈希-------------------------------------------

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hashSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hashGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 哈希判断是否存在key
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Boolean existsHash(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.hasKey(key, hashKey);
    }

    /**
     * hash删除
     *
     * @param key
     * @param hashKey
     */
    public void hashDelete(String key, Object hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    //----------------------------列表-------------------------------------------

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void listSet(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 批量添加
     *
     * @param k
     * @param v
     */
    public void listSetAll(String k, Object... v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPushAll(k, v);
    }

    /**
     * 列表判断
     *
     * @param k
     * @param v
     */
    public Boolean existsListByV(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        List<Object> range = list.range(k, 0, -1);
        return range.contains(v);
    }

    public Boolean existsList(String k, long s, long e) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.size(k) > 0;
    }

    public Boolean existsListByIndex(String k, long index) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.index(k, index) != null;
    }


    /**
     * 列表获取
     *
     * @param k
     * @param start 开始的下标
     * @param end
     * @return
     */
    public List<Object> listGetValue(String k, long start, long end) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, start, end);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param index 下标
     * @return
     */
    public Object listGetValue(String k, long index) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.index(k, index);
    }

    /**
     * 列表删除
     * <p>
     * K key：集合key
     * long count：数量
     * Object value：key对应的值
     *
     * @return
     */
    public Long removeList(String k, long count, Object value) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.remove(k, count, value);

    }

    //----------------------------集合-------------------------------------------

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void setAdd(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }
    public void setAddAll(String key, Object... value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setGetValue(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 集合判断
     *
     * @param key
     * @return
     */
    public Boolean existsSet(String key, Object o) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.isMember(key, o);
    }

    /**
     * 集合删除
     *
     * @param key
     * @return
     */
    public Long removeSet(String key, Object o) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.remove(key, o);
    }

    //----------------------------有序集合(zset)-------------------------------------------

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param score
     */
    public void zSetAdd(String key, Object value, double score) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, score);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param min 0(队头)
     * @param max -1(队尾)
     * @return
     */
    public Set<Object> zSetGetValue(String key, double min, double max) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, min, max);
    }

    public Set<Object> zSetGetValueByIndex(String key, long start, long end) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.range(key, start, end);
    }

    public Boolean existsZSet(String key, String zSetKey) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        Long rank = zset.rank(key, zSetKey);
        return rank != null;
    }

    public Boolean existsZSet(String key) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.size(key) > 0;
    }
}
