package com.zzn.usercenter.utils;

/**
 * ResponseContants
 * 返回统一常量处理
 *
 * @author liufeihua
 * @date 2017/12/5 14:56
 */
public class ResponseContants {

    /**
     * 成功
     */
    public static final int SUCCESS = 200;
    /**
     * 成功消息
     */
    public static final String SUCCESS_MSG = "操作成功";

    public static final String ADD_SUCCESS_MSG = "新增成功";

    public static final String EDIT_SUCCESS_MSG = "编辑成功";

    public static final String DELETE_SUCCESS_MSG = "删除成功";

    /**
     * 请求参数错误
     */
    public static final int PARAMS_ERROR = 101;

    /**
     * 请求参数错误message
     */
    public static final String PARAMS_ERROR_MSG = "请求参数错误";
    /**
     * 用户不存在
     */
    public static final int NOUSERS = 201;
    /**
     * 密码错误
     */
    public static final int ERROR_PASSWORD = 202;
    /**
     * 上传文件失败
     */
    public static final int UPLOAD_FIEL_FAIL = 301;
    /**
     * 上传文件已存在
     */
    public static final int UPLOAD_FIEL_EXIST = 302;
    /**
     * 上传文件不能为空
     */
    public static final int UPLOAD_FIEL_NULL = 303;

    /**
     * ADD操作失败
     */
    public static final int ADD = 1000;
    /**
     * EDIT操作失败
     */
    public static final int EDIT = 1001;
    /**
     * DELETE操作失败
     */
    public static final int DELETE = 1002;
    /**
     * QUERY_EMPTY操作失败
     */
    public static final int QUERY_EMPTY = 1003;
    /**
     * QUERY_EMPTY操作失败
     */
    public static final int QUERY_RESULT_EMPTY = 1004;

    /**
     * 操作失败message
     */
    public static final String ADD_MESSAGE = "添加失败";
    /**
     * 操作失败message
     */
    public static final String EDIT_MESSAGE = "修改失败";
    /**
     * 操作失败message
     */
    public static final String DELETE_MESSAGE = "删除失败";
    /**
     * 操作失败message
     */
    public static final String QUERY_EMPTY_MESSAGE = "查询失败";
    /**
     * 操作失败message
     */
    public static final String QUERY_RESULT_EMPTY_MESSAGE = "查询结果为空";
    /**
     * 上传文件不能为空message
     */
    public static final String UPLOAD_FIEL_NULL_MESSAGE = "查询失败";
    /**
     * 用户注册参数不能有空值
     */
    public static final String REGISTER_PARAM_NULL_MSG = "用户注册参数不能有空值";
    /**
     * 当前用户已存在
     */
    public static final String CURRENT_USER_EXIST_MSG = "当前用户已存在";

}
