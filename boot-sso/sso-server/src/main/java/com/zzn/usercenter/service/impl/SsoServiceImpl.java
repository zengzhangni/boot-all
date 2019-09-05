package com.zzn.usercenter.service.impl;

import com.zzn.usercenter.config.redis.RedisService;
import com.zzn.usercenter.config.sso.Conf;
import com.zzn.usercenter.model.SsoLogin;
import com.zzn.usercenter.model.System;
import com.zzn.usercenter.model.UserCentre;
import com.zzn.usercenter.model.ValidateToken;
import com.zzn.usercenter.model.system.SystemUserRegisterVo;
import com.zzn.usercenter.model.system.SystemUserUpdateVo;
import com.zzn.usercenter.model.vo.User;
import com.zzn.usercenter.model.vo.UserCentreVo;
import com.zzn.usercenter.model.vo.UserRegisterVo;
import com.zzn.usercenter.service.JwtService;
import com.zzn.usercenter.service.SsoService;
import com.zzn.usercenter.service.SystemService;
import com.zzn.usercenter.service.UserCentreService;
import com.zzn.usercenter.utils.ResponseContants;
import com.zzn.usercenter.utils.ResponseMessage;
import com.zzn.usercenter.utils.captcha.ValidateCodeUtil;
import com.zzn.usercenter.utils.generatedcodeutils.UserCentreUtil;
import com.zzn.usercenter.utils.sso.CookieUtil;
import com.zzn.usercenter.utils.sso.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/8/5
 */
@Service
@Slf4j
public class SsoServiceImpl implements SsoService {

    @Resource
    private RedisService redisService;
    @Resource
    private JwtService jwtService;
    @Resource
    private UserCentreService userCentreService;
    @Resource
    private SystemService systemService;

    @Override
    public void login(String redirectUrl, String loginUrl, String ssoToken, String systemCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(ssoToken)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("sso_token".equals(cookie.getName())) {
                        ssoToken = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if (StringUtils.isBlank(ssoToken)) {
            //token 为空 跳到登录页面
            response.sendRedirect(loginUrl + "?redirectUrl=" + redirectUrl);
        } else {
            //验证token
            if (validate(ssoToken, systemCode).getCode() == 200) {
                Cookie cookie = new Cookie("sso_token", ssoToken);
                response.addCookie(cookie);
                //验证成功 跳到receiveTokenUrl
                response.sendRedirect(redirectUrl + "?sso_token=" + ssoToken);
            } else {
                //验证失败 跳到登录页面
                response.sendRedirect(loginUrl + "?redirectUrl=" + redirectUrl);
            }
        }

    }

    @Override
    public ResponseMessage validate(String token, String systemCode) {
        Map<String, Object> info = jwtService.getInfoFromToken(token);
        if (info.get("err_message") != null) {
            log.error("sso_token错误或已失效");
            return new ResponseMessage(500, "sso_token错误或已失效");
        }
        String loginName = info.get("loginName").toString();
        String redisToken = redisService.getStr(loginName);
        if (redisToken == null) {
            log.warn("用户可能已经退出,请重新登录");
            return new ResponseMessage(500, "用户可能已经退出,请重新登录");
        }
        if (token.equals(redisToken)) {
            return new ResponseMessage<>(info);
        } else {
            log.error("无效的sso_token");
            return new ResponseMessage(500, "无效的sso_token");
        }
    }

    @Override
    public String getLoginName(String token) {
        Map<String, Object> info;
        try {
            info = jwtService.getInfoFromToken(token);
            return info.get("loginName").toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String creationToken(Map<String, Object> userInfo) {
        //根据systemUserCode查询loginName
        String token = jwtService.generateToken(userInfo);
        redisService.setStr(userInfo.get("loginName").toString(), token, 640000L);
        return token;
    }

    @Override
    public void ssoLogin(SsoLogin ssoLogin, HttpServletRequest request, HttpServletResponse response) {
        String redirectUrl = ssoLogin.getRedirectUrl();
        String loginUrl = ssoLogin.getLoginUrl();
        String ssoToken = ssoLogin.getSsoToken();
        String systemCode = ssoLogin.getSystemCode();
        try {
            login(redirectUrl, loginUrl, ssoToken, systemCode, request, response);
        } catch (IOException e) {
            log.error("sso登录异常");
        }
    }

    @Override
    public ResponseMessage validateSsoToken(ValidateToken validateToken) {
        String ssoToken = validateToken.getSsoToken();
        String systemCode = validateToken.getSystemCode();
        return validate(ssoToken, systemCode);
    }

    @Override
    public void ssoLogout(String loginName) {
        redisService.remove(loginName);
    }

    @Override
    public ResponseMessage<String> getTokenByLogin(String loginName, String password, HttpServletRequest request, HttpServletResponse response) {
        UserCentre userCentre = userCentreService.queryByLoginName(loginName);
        if (userCentre == null) {
            return new ResponseMessage<>(500, "用户不存在!");
        } else if (!userCentre.getLoginPassword().equals(password)) {
            return new ResponseMessage<>(500, "密码错误!");
        }
        Map<String, Object> userInfo = new HashMap<>(2);
        userInfo.put("loginName", userCentre.getLoginName());
        userInfo.put("loginPassword", userCentre.getLoginPassword());
        userInfo.put("phone", userCentre.getPhone());
        userInfo.put("gender", userCentre.getGender());
        String token = creationToken(userInfo);
        return new ResponseMessage<>(token);
    }

    @Override
    public ResponseMessage doLogin(User user, HttpServletRequest request, HttpServletResponse response) {

        ResponseMessage<String> message = getTokenByLogin(user.getLoginName(), Md5Util.md5(user.getPassword()), request, response);
        if (message.getCode() != 200) {
            return new ResponseMessage<>(500, message.getMessage());
        }
        String redirectUrl = user.getRedirectUrl();
        if (redirectUrl != null && redirectUrl.trim().length() > 0) {
            String systemCode = user.getSystemCode();
            String signature = user.getSignature();
            if (systemCode != null && signature != null) {
                System system = systemService.queryBySystemCode(systemCode);
                if (system == null) {
                    return new ResponseMessage<>(500, "访问系统不存在!");
                } else {
                    String systemKey = system.getSystemKey();
                    Map<String, String> map = new HashMap<>(2);
                    map.put("redirect_url", redirectUrl);
                    map.put("system_code", systemCode);
                    if (Md5Util.decoding(signature, map, systemKey)) {
                        String redirectUrlFinal = redirectUrl + "?" + Conf.SSO_TOKEN + "=" + message.getData();
                        CookieUtil.set(response, Conf.SSO_TOKEN, message.getData(), true);
                        return new ResponseMessage<>(200, "登录成功", redirectUrlFinal);
                    } else {
                        return new ResponseMessage<>(500, "签名验证失败!");
                    }
                }
            } else {
                return new ResponseMessage<>(500, "参数缺失,找不到访问系统!");
            }
        } else {
            CookieUtil.set(response, Conf.SSO_TOKEN, message.getData(), true);
            return new ResponseMessage<>(200, "登录成功", "/");
        }
    }

    @Override
    public String getVerificationCode(String loginName) {
        ValidateCodeUtil.Validate v = ValidateCodeUtil.getRandomCode();
        redisService.setStr(loginName, v.getValue().toLowerCase(), 120L);
        return v.getBase64Str();
    }

    @Override
    public ResponseMessage doRegister(UserRegisterVo userInfo) {
        String str = redisService.getStr(userInfo.getRegisterName());
        if (userInfo.getVerifyCode().toLowerCase().equals(str)) {
            UserCentreVo userCentreVo = new UserCentreVo();
            userCentreVo.setLoginName(userInfo.getRegisterName());
            userCentreVo.setLoginPassword(Md5Util.md5(userInfo.getRegisterPassword()));
            return userCentreService.userRegister(userCentreVo);
        } else {
            return new ResponseMessage(500, "验证码过期或错误!");
        }

    }

    /**
     * ------------------系统-------------
     */

    @Override
    public ResponseMessage verifyLoginName(String loginName) {
        UserCentre userCentre = userCentreService.queryByLoginName(loginName);
        if (userCentre == null) {
            return new ResponseMessage<>();
        } else {
            return new ResponseMessage<>(500, ResponseContants.CURRENT_USER_EXIST_MSG);
        }
    }

    @Override
    public ResponseMessage systemUserRegister(SystemUserRegisterVo systemUserRegisterVo) {
        return userCentreService.systemUserRegister(systemUserRegisterVo);
    }

    @Override
    public ResponseMessage systemUserUpdate(SystemUserUpdateVo vo) {
        if (StringUtils.isNotBlank(vo.getNewLoginName())) {
            UserCentre userCentre1 = userCentreService.queryByLoginName(vo.getNewLoginName());
            if (userCentre1 != null) {
                return new ResponseMessage(500, "用户已存在!");
            }
        }
        if (StringUtils.isNotBlank(vo.getPassword()) && StringUtils.isNotBlank(vo.getNewPassword())) {
            UserCentre userCentre = userCentreService.queryByLoginName(vo.getLoginName());
            if (userCentre == null) {
                userCentre = new UserCentre();
                userCentre.setUserCode(UserCentreUtil.getUserCentreCode());
                userCentre.setLoginName(vo.getLoginName());
                userCentre.setLoginPassword(Md5Util.md5(vo.getNewPassword()));
                userCentre.setPhone(vo.getPhone());
                userCentre.setEmail(vo.getEmail());
                userCentre.setSystemSource(vo.getSystemCode());
                int insert = userCentreService.insert(userCentre);
                return new ResponseMessage<>(insert);
            } else {
                if (userCentre.getLoginPassword().equals(vo.getPassword())) {
                    vo.setNewPassword(Md5Util.md5(vo.getNewPassword()));
                    return userCentreService.systemUserUpdate(vo);
                } else {
                    return new ResponseMessage(500, "原密码错误!");
                }
            }
        } else {
            return userCentreService.systemUserUpdate(vo);
        }
    }

}
