package com.zzn.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zengzhangni
 * @date 2019/9/9
 */
@Data
public class RefundVo extends BaseVo {

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

    @ApiModelProperty("备注")
    private String remark;


}
