package com.zzn.sms.test.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/5/9
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "发送短信条件")
public class SmsMessage implements Serializable {


    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("短信编码")
    private String templateCode;

    @ApiModelProperty("短信内容 替换模板内容")
    private Map<String,Object> smsContent;
}
