package com.zzn.usercenter.aop;

import com.zzn.usercenter.config.redis.RedisService;
import com.zzn.usercenter.config.sso.Conf;
import com.zzn.usercenter.model.Log;
import com.zzn.usercenter.model.vo.User;
import com.zzn.usercenter.service.LogService;
import com.zzn.usercenter.service.SsoService;
import com.zzn.usercenter.utils.sso.CookieUtil;
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

/**
 * @author zengzhangni
 * @date 2019/8/19
 */
@Component
@Aspect
@Order(2)
@Slf4j
public class LogRecordAspect {

    @Resource
    private LogService logService;
    @Resource
    private SsoService ssoService;
    @Resource
    private RedisService redisService;

    @Pointcut("@annotation(com.zzn.usercenter.aop.LogRecord)")
    public void logRecord() {

    }

    @Around("logRecord()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //得到请求的资源
        String requestUri = request.getRequestURI();
        //得到来访者的IP地址
        String remoteAddr = request.getRemoteAddr();
        //获取请求类型
        String method = request.getMethod();

        Log log = new Log();
        if ("/sso/doLogin".equals(requestUri)) {
            //如果登录从user中获取登录人
            User user = (User) joinPoint.getArgs()[0];
            log.setOperateName(user.getLoginName());
            log.setRedirectUrl(user.getRedirectUrl());
            log.setSystemCode(user.getSystemCode());

        } else {
            //其他请求重cookie中获取ssoToken解析获取登录人
            String ssoToken = CookieUtil.getValue(request, Conf.SSO_TOKEN);
            String loginName = ssoService.getLoginName(ssoToken);
            log.setOperateName(loginName);
            log.setRedirectUrl(request.getParameter(Conf.REDIRECT_URL));
            log.setSystemCode(request.getParameter(Conf.SYSTEM_CODE));
        }
        log.setMethod(method);
        log.setRequestUrl(requestUri);
        log.setRemoteHost(remoteAddr);
        logService.asyncAddLog(log);
        return joinPoint.proceed();
    }


}
