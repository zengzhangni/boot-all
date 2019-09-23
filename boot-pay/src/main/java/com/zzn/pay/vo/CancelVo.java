package com.zzn.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: xcj
 * @Date: 2019/9/10 15:28
 * @Description:
 */
@Data
public class CancelVo extends BaseVo {

    @NotBlank(message = "金额不能为空")
    @ApiModelProperty("金额(单位为分)")
    private String amount;

    @NotBlank(message = "退款订单号不能为空")
    @ApiModelProperty("退款订单号(商户平台唯一)")
    private String refundNo;

    @ApiModelProperty("交易订单号(orderNo和platformOrderNo必填其一)")
    private String oldOrderNo;

    @ApiModelProperty("商户订单号(平台返回)")
    private String oldMerchantOrderNo;


}
