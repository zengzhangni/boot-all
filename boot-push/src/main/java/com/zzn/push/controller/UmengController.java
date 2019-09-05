package com.zzn.push.controller;

import com.zzn.push.config.ResponseMessage;
import com.zzn.push.model.PushMsgParamVo;
import com.zzn.push.service.UmengService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "友盟推送")
@RestController
@RequestMapping(value = "umeng")
public class UmengController {


    @Resource
    private UmengService uMengService;

    @ApiOperation("推送消息")
    @PostMapping(value="/pushMessage")
    public ResponseMessage pushMessage(@RequestBody List<PushMsgParamVo> pushMsgParamVoList){
        return this.uMengService.pushMessage(pushMsgParamVoList);
    }

}
