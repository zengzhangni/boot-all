package com.zzn.sms.test.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/6/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "微信模板消息条件Vo")
public class WxSendVo {
    @ApiModelProperty("会员uuid")
    private String memberUuid;
    @ApiModelProperty("会员openId")
    private String openId;
    @ApiModelProperty("模板消息p编码")
    private String pTemplateCode;
    @ApiModelProperty("动态内容替换")
    private Object[] args;
    @ApiModelProperty("模板跳转链接")
    private String url;
    @ApiModelProperty("模板内容字体颜色，不填默认为黑色")
    private String color;
    @ApiModelProperty("跳小程序所需数据")
    private Map miniProgram;
}
