package com.zzn.usercenter.service;

import com.zzn.usercenter.model.SsoLogin;
import com.zzn.usercenter.model.ValidateToken;
import com.zzn.usercenter.model.system.SystemUserRegisterVo;
import com.zzn.usercenter.model.system.SystemUserUpdateVo;
import com.zzn.usercenter.model.vo.User;
import com.zzn.usercenter.model.vo.UserRegisterVo;
import com.zzn.usercenter.utils.ResponseMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/8/5
 */
public interface SsoService {


    void login(String redirectUrl, String loginUrl, String ssoToken, String systemCode, HttpServletRequest request, HttpServletResponse response) throws IOException;

    ResponseMessage validate(String token, String systemCode);

    String getLoginName(String token);

    String creationToken(Map<String, Object> userInfo);

    void ssoLogin(SsoLogin ssoLogin, HttpServletRequest request, HttpServletResponse response);

    ResponseMessage validateSsoToken(ValidateToken validateToken);

    void ssoLogout(String loginName);

    ResponseMessage<String> getTokenByLogin(String loginName, String password, HttpServletRequest request, HttpServletResponse response);

    ResponseMessage doLogin(User user, HttpServletRequest request, HttpServletResponse response);

    String getVerificationCode(String loginName);

    ResponseMessage doRegister(UserRegisterVo userInfo);


    /**
     *------------------系统-------------------
     */

    ResponseMessage verifyLoginName(String loginName);

    ResponseMessage systemUserRegister(SystemUserRegisterVo systemUserRegisterVo);

    ResponseMessage systemUserUpdate(SystemUserUpdateVo systemUserUpdateVo);

}
