package com.zzn.core.filter;

import com.alibaba.fastjson.JSONObject;
import com.zzn.core.conf.Conf;
import com.zzn.core.path.impl.AntPathMatcher;
import com.zzn.core.util.CookieUtil;
import com.zzn.core.util.HttpUtil;
import com.zzn.core.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * sso核心过滤器
 *
 * @author zengzhangni
 * @date 2019/8/19
 */
public class SsoWebFilter extends HttpServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(SsoWebFilter.class);

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private String ssoServer;
    private String logoutPath;
    private String systemIndexPath;
    private String excludedPaths;
    private String systemCode;
    private String systemKey;

    @Override
    public void init(FilterConfig filterConfig) {

        ssoServer = filterConfig.getInitParameter(Conf.SSO_SERVER);
        logoutPath = filterConfig.getInitParameter(Conf.SSO_LOGOUT_PATH);
        systemIndexPath = filterConfig.getInitParameter(Conf.SYSTEM_INDEX_PATH);
        excludedPaths = filterConfig.getInitParameter(Conf.SSO_EXCLUDED_PATHS);
        systemCode = filterConfig.getInitParameter(Conf.SYSTEM_CODE);
        systemKey = filterConfig.getInitParameter(Conf.SYSTEM_KEY);
        logger.info("SsoWebFilter 初始化....");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 获取请求 url
        String servletPath = req.getServletPath();
        // 判断请求是否忽略路径
        if (excludedPaths != null && excludedPaths.trim().length() > 0) {
            for (String excludedPath : excludedPaths.split(",")) {
                String uriPattern = excludedPath.trim();
                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    // excluded path 放行
                    chain.doFilter(request, response);
                    return;
                }

            }
        }
        // 是否是退出
        if (logoutPath != null
                && logoutPath.trim().length() > 0
                && logoutPath.equals(servletPath)) {

            // 删除 cookie中sso_token
            CookieUtil.remove(req, res, Conf.SSO_TOKEN);
            CookieUtil.remove(req, res, Conf.IS_TOKEN);
            //获取首页地址
            String redirectUrl = systemIndexPath;
            //对参数进行排序加密
            String plainText = String.format("redirect_url=%s&system_code=%s", redirectUrl, systemCode) + systemKey;
            String signature = Md5Util.md5(plainText);
            // 重定向到sso 退出登录
            String logoutPageUrl = ssoServer.concat(Conf.SSO_LOGOUT) + "?" + Conf.REDIRECT_URL + "=" + redirectUrl + "&system_code=" + systemCode + "&signature=" + signature;
            res.sendRedirect(logoutPageUrl);
            return;
        }
        //先从地址获取ssoToken,因为sso重定向是将ssoToken拼接在地址上
        String ssoToken = req.getParameter(Conf.SSO_TOKEN);
        if (ssoToken == null) {
            //地址获取不到再从Cookie ssoToken
            ssoToken = CookieUtil.getValue(req, Conf.SSO_TOKEN);
        }
        if (ssoToken != null && ssoToken.length() > 0) {
            //sso_token 不为null 到sso服务验证token
            String url = ssoServer + "/sso/validate?ssoToken=" + ssoToken;
            //获取响应
            String info = HttpUtil.doGet(url);
            Map map = JSONObject.parseObject(info, Map.class);
            //sso_token 验证成功 存入自身cookie 以后请求可以从本身cookie获取
            if ("200".equals(map.get("code").toString())) {
                String isToken = req.getParameter(Conf.IS_TOKEN);
                if (isToken == null) {
                    isToken = CookieUtil.getValue(req, Conf.IS_TOKEN);
                }
                if (Boolean.valueOf(isToken)) {
                    CookieUtil.set(res, Conf.SSO_TOKEN, ssoToken, true);
                    CookieUtil.set(res, Conf.IS_TOKEN, isToken, true);
                    //将sso_token解析的用户信息添加到request中 接口通过 @RequestAttribute("userCentre") Map userCentre 获取
                    Map data = (Map) map.get("data");
                    data.put("ssoToken", ssoToken);
                    request.setAttribute("userCentre", data);
                    chain.doFilter(request, response);
                } else {
                    res.sendRedirect(ssoServer + "/sso/saveSsoToken?ssoToken=" + ssoToken + "&redirectUrl=" + req.getRequestURL().toString());
                }
            } else {
                //token 验证失败 重定向sso登录页面 带上请求地址
                String link = req.getRequestURL().toString();
                redirect(req, res, link);
            }
        } else {
            //token 不存在 重定向sso登录页面 带上请求地址
            String link = req.getRequestURL().toString();
            redirect(req, res, link);
        }
    }

    private void redirect(HttpServletRequest req, HttpServletResponse res, String link) throws IOException {
        String header = req.getHeader("Content-Type");
        boolean isJson = header != null && header.contains("json");
        if (isJson) {
            res.setStatus(401);
            res.setContentType("application/json;charset=utf-8");
            res.getWriter().println("{\"code\":" + 401 + ", \"msg\":\"" + "请登录" + "\"}");
        } else {
            //对参数进行排序加密
            String plainText = String.format("redirect_url=%s&system_code=%s", link, systemCode) + systemKey;
            String signature = Md5Util.md5(plainText);
            String loginPageUrl = ssoServer.concat(Conf.SSO_LOGIN) + "?redirect_url=" + link + "&system_code=" + systemCode + "&signature=" + signature;
            res.sendRedirect(loginPageUrl);
        }

    }

}
