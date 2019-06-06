package com.zzn.common.exception;

import com.zzn.common.response.ResponseMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常统一处理
 *
 * @author zengzhangni
 * @date 2019/5/21
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 空指针异常处理
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseMessage handleException(NullPointerException ex) {
        return new ResponseMessage<>(120500, "空指针异常!", ex.getStackTrace()[0]);
    }

}
