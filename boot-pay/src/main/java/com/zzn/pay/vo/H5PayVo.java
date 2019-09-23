package com.zzn.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zengzhangni
 * @date 2019/9/9
 */
@Data
public class H5PayVo extends BaseVo {

    @NotBlank(message = "金额不能为空")
    @ApiModelProperty("金额(单位为分)")
    private String amount;

    @NotBlank(message = "交易订单号不能为空")
    @ApiModelProperty("交易订单号")
    private String orderNo;

    @NotBlank(message = "通知页面路径不能为空")
    @ApiModelProperty("页面跳转同步通知页面路径")
    private String returl;

    @NotBlank(message = "异步通知页面路径不能为空")
    @ApiModelProperty("服务器异步通知页面路径")
    private String notifyUrl;

    @NotBlank(message = "订单标题不能为空")
    @ApiModelProperty("订单标题")
    private String body;

    @ApiModelProperty("订单备注信息")
    private String remark;

}
