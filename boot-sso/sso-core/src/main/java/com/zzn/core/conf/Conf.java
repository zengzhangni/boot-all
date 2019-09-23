package com.zzn.core.conf;


/**
 * sso 常量配置
 *
 * @author zengzhangni
 * @date 2019/8/19
 */
public class Conf {


    /**
     * 重定向常量
     */
    public static final String REDIRECT_URL = "redirect_url";


    /**
     * SSO 服务地址
     */
    public static final String SSO_SERVER = "sso_server";

    /**
     * login url
     */
    public static final String SSO_LOGIN = "/login";
    /**
     * logout url
     */
    public static final String SSO_LOGOUT = "/logout";


    /**
     * 注销路径，客户端相对路径 如:/logout
     */
    public static final String SSO_LOGOUT_PATH = "sso_logout_path";
    /**
     * 系统首页 退出时跳转
     */
    public static final String SYSTEM_INDEX_PATH = "system_index_path";

    /**
     * 排除路径
     */
    public static final String SSO_EXCLUDED_PATHS = "sso_excluded_paths";
    /**
     * sso_token
     */
    public static final String SSO_TOKEN = "sso_token";
    /**
     * sso cookie 是否拥有token
     */
    public static final String IS_TOKEN = "is_token";
    /**
     * 系统编码(sso生成)
     */
    public static final String SYSTEM_CODE = "system_code";
    /**
     * 接口鉴权key
     */
    public static final String SYSTEM_KEY = "system_key";


}
