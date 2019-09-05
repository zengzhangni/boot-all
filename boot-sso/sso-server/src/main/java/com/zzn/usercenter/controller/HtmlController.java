package com.zzn.usercenter.controller;

import com.zzn.usercenter.aop.LogRecord;
import com.zzn.usercenter.aop.ValidationSystem;
import com.zzn.usercenter.config.redis.RedisService;
import com.zzn.usercenter.config.sso.Conf;
import com.zzn.usercenter.model.System;
import com.zzn.usercenter.service.SsoService;
import com.zzn.usercenter.service.SystemService;
import com.zzn.usercenter.utils.ResponseMessage;
import com.zzn.usercenter.utils.sso.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/8/15
 */
@Controller
public class HtmlController {

    @Resource
    private SsoService ssoService;
    @Resource
    private RedisService redisService;
    @Resource
    private SystemService systemService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        String ssoToken = CookieUtil.getValue(request, Conf.SSO_TOKEN);
        if (StringUtils.isNotBlank(ssoToken)) {
            ResponseMessage validate = ssoService.validate(ssoToken, request.getParameter(Conf.SYSTEM_CODE));
            if (validate.getCode() == 200) {
                Map map = (Map) validate.getData();
                request.setAttribute("userCentre", map);
                return "index";
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/login1")
    @ValidationSystem
    public String login1(HttpServletRequest request, HttpServletResponse response) {
        String ssoToken = CookieUtil.getValue(request, Conf.SSO_TOKEN);
        if (ssoToken == null) {
            response.setStatus(401);
            return "login";
        }
        ResponseMessage validate = ssoService.validate(ssoToken, "");
        if (validate.getCode() != 200) {
            response.setStatus(401);
            return "login";
        } else {
            String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
            if (redirectUrl != null && redirectUrl.trim().length() > 0) {
                String redirectUrlFinal = redirectUrl + "?" + Conf.SSO_TOKEN + "=" + ssoToken;
                return "redirect:" + redirectUrlFinal;
            } else {
                return "redirect:/";
            }
        }
    }



    @GetMapping("/login")
    @ValidationSystem
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String ssoToken = CookieUtil.getValue(request, Conf.SSO_TOKEN);
        String systemCode = request.getParameter(Conf.SYSTEM_CODE);
        if (systemCode != null) {
            System system = systemService.queryBySystemCode(systemCode);
            String systemDomain = system.getSystemDomain();
            String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
            String signature = request.getParameter(Conf.SIGNATURE);
            if (ssoToken == null) {
                String url = systemDomain + "?redirect_url=" + redirectUrl + "&system_code=" + systemCode + "&signature=" + signature;
                response.setStatus(401);
                return "redirect:" + url;
            } else {
                ResponseMessage validate = ssoService.validate(ssoToken, systemCode);
                if (validate.getCode() != 200) {
                    String url = systemDomain + "?redirect_url=" + redirectUrl + "&system_code=" + systemCode + "&signature=" + signature;
                    response.setStatus(401);
                    return "redirect:" + url;
                } else {
                    String redirectUrlFinal = redirectUrl + "?" + Conf.SSO_TOKEN + "=" + ssoToken;
                    return "redirect:" + redirectUrlFinal;
                }
            }
        } else {
            if (ssoToken == null) {
                response.setStatus(401);
                return "login";
            } else {
                ResponseMessage validate = ssoService.validate(ssoToken, systemCode);
                if (validate.getCode() != 200) {
                    response.setStatus(401);
                    return "login";
                } else {
                    return "redirect:/";
                }
            }
        }
    }


    @GetMapping("/logout")
    @ValidationSystem
    @LogRecord
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String ssoToken = CookieUtil.getValue(request, Conf.SSO_TOKEN);
        String systemCode = request.getParameter(Conf.SYSTEM_CODE);
        if (StringUtils.isNotBlank(ssoToken)) {
            ResponseMessage validate = ssoService.validate(ssoToken, systemCode);
            if (validate.getCode() == 200) {
                Map map = (Map) validate.getData();
                String loginName = map.get("loginName").toString();
                redisService.removeStr(loginName);
            }
        }
        CookieUtil.remove(request, response, Conf.SSO_TOKEN);
        String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
        String signature = request.getParameter(Conf.SIGNATURE);
        if (redirectUrl != null) {
            return "redirect:/login?redirect_url=" + redirectUrl + "&system_code=" + systemCode + "&signature=" + signature;
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/log")
    public String log() {
        return "log";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/system")
    public String system() {
        return "system";
    }


}
