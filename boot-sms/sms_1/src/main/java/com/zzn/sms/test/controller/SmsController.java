package com.zzn.sms.test.controller;

import com.aliyuncs.exceptions.ClientException;
import com.zzn.common.response.ResponseMessage;
import com.zzn.sms.test.message.SmsMessage;
import com.zzn.sms.test.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zengzhangni
 * @date 2019/5/9
 */
@Api(tags = "短信")
@RestController
@RequestMapping("sms")
public class SmsController {

    @Resource
    private SmsService smsService;

    @PostMapping("/smsMessage")
    @ApiOperation(value = "发送消息")
    public ResponseMessage<String> smsMessage(@RequestBody(required = false) SmsMessage message) {
        try {
            return smsService.sendSmsMessage(message);
        } catch (ClientException e) {
            return new ResponseMessage<>("no");
        }
    }



}
