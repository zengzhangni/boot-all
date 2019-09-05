package com.zzn.usercenter.exception;

import com.alibaba.fastjson.JSONObject;
import com.zzn.usercenter.utils.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/5/21
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 统一处理 BindException 错误
     *
     * @param ex 参数验证失败错误
     * @return 参数验证失败响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseMessage processBindException(MethodArgumentNotValidException ex) {
        // 获取错误信息
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        // 目前消息只返回一条错误信息，所以只需要取第一条错误信息即可
        Map<String, String> errorMap = null;
        if (fieldErrors.size() > 0) {
            errorMap = new HashMap<>(fieldErrors.size());
            for (FieldError fieldError : fieldErrors) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return new ResponseMessage<>(500, "参数校验异常", errorMap);
    }

    /**
     * 空指针异常处理
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseMessage handleException(NullPointerException ex) {
        log.error("空指针异常!:errmsg:{}", ex.getStackTrace()[0]);
        return new ResponseMessage<>(500, "空指针异常!", ex.getStackTrace()[0]);
    }


    /**
     * 类型转换异常处理
     */
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public ResponseMessage handleException(ClassCastException ex) {
        log.error("类型转换异常!:errmsg:{},err:{}", ex.getMessage(), ex.getStackTrace()[0]);
        return new ResponseMessage<>(500, "类型转换异常!" + ex.getMessage(), ex.getStackTrace()[0]);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseMessage exception(Exception ex) {
        log.error(JSONObject.toJSON(ex.getStackTrace()[0]).toString());
        return new ResponseMessage<>(500, "未知异常!");
    }


}
