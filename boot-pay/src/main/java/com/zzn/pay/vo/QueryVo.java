package com.zzn.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: xcj
 * @Date: 2019/9/10 14:54
 * @Description:
 */
@Data
public class QueryVo extends BaseVo {

    @ApiModelProperty("交易订单号(orderNo和platformOrderNo必填其一)")
    private String orderNo;

    @ApiModelProperty("商户订单号(平台返回)")
    private String merchantOrderNo;

}
