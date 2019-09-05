package com.zzn.usercenter.controller;

import com.zzn.usercenter.aop.LogRecord;
import com.zzn.usercenter.config.sso.Conf;
import com.zzn.usercenter.model.SsoLogin;
import com.zzn.usercenter.model.ValidateToken;
import com.zzn.usercenter.model.system.SystemUserRegisterVo;
import com.zzn.usercenter.model.system.SystemUserUpdateVo;
import com.zzn.usercenter.model.vo.User;
import com.zzn.usercenter.model.vo.UserRegisterVo;
import com.zzn.usercenter.service.SsoService;
import com.zzn.usercenter.utils.ResponseMessage;
import com.zzn.usercenter.utils.sso.CookieUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/8/5
 */
@Api(tags = "SSO")
@RestController
@RequestMapping("sso")
public class SsoController {


    @Resource
    private SsoService ssoService;

    /**
     * @param redirectUrl 资源地址
     * @param loginUrl    登录地址
     * @param request     请求对象
     * @param response    响应对象
     * @throws IOException 异常
     */
    @ApiOperation("单点登录验证")
    @GetMapping("/login")
    public void login(@RequestParam("redirectUrl") String redirectUrl, @RequestParam("loginUrl") String loginUrl, @RequestParam(value = "ssoToken", required = false) String ssoToken, @RequestParam("systemCode") String systemCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ssoService.login(redirectUrl, loginUrl, ssoToken, systemCode, request, response);
    }


    @ApiOperation("单点登录验证")
    @PostMapping("/ssoLogin")
    public void ssoLogin(@RequestBody SsoLogin ssoLogin, HttpServletRequest request, HttpServletResponse response) {
        ssoService.ssoLogin(ssoLogin, request, response);
    }

    @ApiOperation("单点退出")
    @GetMapping("/ssoLogout")
    public void ssoLogout(@RequestParam("loginName") String loginName) {
        ssoService.ssoLogout(loginName);
    }


    /**
     * @param userInfo 用户信息
     */
    @ApiOperation("获取ssoToken userInfo至少存在loginName字段")
    @PostMapping("/getToken")
    public ResponseMessage getToken(@RequestBody Map<String, Object> userInfo) {
        if (userInfo.get("loginName") == null) {
            return new ResponseMessage<>(500, "参数缺失");
        }
        return new ResponseMessage<>(ssoService.creationToken(userInfo));

    }


    /**
     * @param ssoToken token
     * @return 验证信息
     */
    @ApiOperation("验证ssoToken")
    @GetMapping("/validate")
    public ResponseMessage validate(@RequestParam("ssoToken") String ssoToken, @RequestParam(value = "systemCode", required = false) String systemCode) {
        return ssoService.validate(ssoToken, systemCode);
    }

    @ApiOperation("验证ssoToken")
    @PostMapping("/validateSsoToken")
    public ResponseMessage validateSsoToken(@RequestBody ValidateToken validateToken) {
        return ssoService.validateSsoToken(validateToken);
    }


    @ApiOperation("登录")
    @PostMapping("/doLogin")
    @LogRecord
    public ResponseMessage doLogin(@RequestBody @Valid User user, HttpServletRequest request, HttpServletResponse response) {
        return ssoService.doLogin(user, request, response);
    }

    @ApiOperation("注册")
    @PostMapping("/doRegister")
    public ResponseMessage doRegister(@RequestBody @Valid UserRegisterVo userInfo) {
        return ssoService.doRegister(userInfo);
    }

    /**
     * 刷新验证码
     */
    @ApiOperation("获取验证码")
    @GetMapping("/getVerificationCode")
    public ResponseMessage<String> getVerificationCode(@RequestParam("loginName") String loginName) {
        return new ResponseMessage<>(ssoService.getVerificationCode(loginName));
    }

    @GetMapping("/saveSsoToken")
    public void saveSsoToken(@RequestParam("ssoToken") String ssoToken, @RequestParam("redirectUrl") String redirectUrl, HttpServletResponse response) throws IOException {
        CookieUtil.set(response, Conf.SSO_TOKEN, ssoToken, true);
        response.sendRedirect(redirectUrl + "?sso_token=" + ssoToken + "&is_token=" + true);
    }

    /**
     * -----------------------------系统调用--------------------------------------------
     */

    @ApiOperation("验证用户名是否存在")
    @GetMapping("/verifyLoginName")
    public ResponseMessage verifyLoginName(@RequestParam("loginName") String loginName) {
        return ssoService.verifyLoginName(loginName);
    }

    @ApiOperation("系统用户注册")
    @PostMapping("/systemUserRegister")
    public ResponseMessage systemUserRegister(@RequestBody @Valid SystemUserRegisterVo systemUserRegisterVo) {
        return ssoService.systemUserRegister(systemUserRegisterVo);
    }

    @ApiOperation("系统用户更新")
    @PostMapping("/systemUserUpdate")
    public ResponseMessage systemUserUpdate(@RequestBody @Valid SystemUserUpdateVo systemUserUpdateVo) {
        return ssoService.systemUserUpdate(systemUserUpdateVo);
    }


}
