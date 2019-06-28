package com.zzn.sms.test.controller;

import com.zzn.common.response.ResponseMessage;
import com.zzn.sms.test.message.WxSendVo;
import com.zzn.sms.test.service.WxSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/3/26
 */
@Api(tags = "发送微信模板消息")
@RestController
@RequestMapping(value = "wxSend")
public class WxSendController {

    @Resource
    private WxSendService wxSendService;



    @ApiOperation(value = "发送微信模板消息")
    @PostMapping("/newSendWxMsg")
    public ResponseMessage<Map> sendWxMsg(@RequestBody WxSendVo wxSendVo) {
        return this.wxSendService.sendWxMsg(wxSendVo);
    }

    /**
     * 微信获取位置
     *
     * @param platformCode
     * @param url
     * @return
     */
    @ApiOperation(value = "微信获取位置")
    @PostMapping("/toObtainPosition")
    public ResponseMessage<Map> toObtainPosition(@RequestParam("platformCode") String platformCode, @RequestParam("url") String url) {
        return this.wxSendService.toObtainPosition(platformCode, url);
    }


}
