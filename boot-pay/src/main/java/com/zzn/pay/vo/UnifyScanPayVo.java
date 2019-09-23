package com.zzn.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: xcj
 * @Date: 2019/9/10 09:51
 * @Description:
 */
@Data
public class UnifyScanPayVo extends BaseVo {

    @NotBlank(message = "金额不能为空")
    @ApiModelProperty("金额(单位为分)")
    private String amount;

    @NotBlank(message = "交易订单号不能为空")
    @ApiModelProperty("交易订单号")
    private String orderNo;

    @NotBlank(message = "支付授权码不能为空")
    @ApiModelProperty("支付授权码(如微信,支付宝,银联的付款二维码)")
    private String authCode;

    @ApiModelProperty("订单标题(为空则以商户名作为商品名称)")
    private String body;

    @ApiModelProperty("备注")
    private String remark;



}
