package com.zzn.usercenter.aop;

import java.lang.annotation.*;

/**
 * 日志记录
 *
 * @author zengzhangni
 * @date 2019/8/19
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecord {
}
