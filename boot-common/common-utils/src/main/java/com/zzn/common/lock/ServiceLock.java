package com.zzn.common.lock;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLock {

    /**
     * key是lock 的key ,也是redis的key
     * 使用 '#'动态获取key值  不支持Map  '#map.name'
     * <p>
     * 1.获取参数作为key
     * ServiceLock(key="#code")
     * method(String code)
     * key = 123
     * <p>
     * 2.获取对象属性作为key
     * ServiceLock(key="#user.name")
     * method(User user)
     * key = 姓名
     * <p>
     * 3.多参数作为key
     * ServiceLock(key="#user.name+#id")
     * method(String code,String id)
     * key = nameid
     * <p>
     * 4.固定值作为key
     * ServiceLock(key="固定值")
     * method()
     * key = 固定值
     */
    String key() default "key";


    /**
     * redis 存值类型的固定key
     * [hash,set,zet,list]必须声明typeKey
     */
    String typeKey() default "";

    /**
     * redis 存值方式[object,string,hash,set,list,zset]
     * 默认对象存储
     */
    TypeKey type() default TypeKey.object;

    /**
     * 下标 开始 [set,zet,list]需要声明start
     */
    String start() default "0";

    /**
     * 下标 结束[set,zet,list]需要声明end
     */
    String end() default "0";

    /**
     * 下标[list]可以直接使用获取某个值,也可以使用[start-end]获取多个值
     * index 有值优先使用index 获取
     */
    String index() default "";

}
