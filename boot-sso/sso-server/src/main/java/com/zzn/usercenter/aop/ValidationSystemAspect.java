package com.zzn.usercenter.aop;

import com.zzn.usercenter.config.sso.Conf;
import com.zzn.usercenter.model.System;
import com.zzn.usercenter.service.SystemService;
import com.zzn.usercenter.utils.sso.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/8/19
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class ValidationSystemAspect {

    @Resource
    private SystemService systemService;

    @Pointcut("@annotation(com.zzn.usercenter.aop.ValidationSystem)")
    public void validationSystem() {

    }

    @Around("validationSystem()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String systemCode = request.getParameter(Conf.SYSTEM_CODE);
        String signature = request.getParameter(Conf.SIGNATURE);
        String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
        if (redirectUrl != null) {
            if (systemCode != null && signature != null) {
                System system = systemService.queryBySystemCode(systemCode);
                if (system == null) {
                    request.setAttribute("msg", "访问系统不存在!");
                    return "404";
                } else {
                    String systemKey = system.getSystemKey();
                    Map<String, String> map = new HashMap<>(2);
                    map.put("redirect_url", redirectUrl);
                    map.put("system_code", systemCode);
                    if (Md5Util.decoding(signature, map, systemKey)) {
                        return joinPoint.proceed();
                    } else {
                        request.setAttribute("msg", "签名验证失败!");
                        return "404";
                    }
                }
            } else {
                request.setAttribute("msg", "参数缺失,找不到访问系统!");
                return "404";
            }
        }
        return joinPoint.proceed();
    }


}
