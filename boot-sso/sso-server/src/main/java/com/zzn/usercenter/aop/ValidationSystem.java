package com.zzn.usercenter.aop;

import java.lang.annotation.*;

/**
 * 系统效验
 *
 * @author zengzhangni
 * @date 2019/8/19
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidationSystem {
}
