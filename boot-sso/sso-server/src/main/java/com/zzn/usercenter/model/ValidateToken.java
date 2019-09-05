package com.zzn.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zengzhangni
 * @date 2019/8/9
 */
@Data
@ApiModel(value = "验证Token条件")
public class ValidateToken implements Serializable {

    @ApiModelProperty("ssoToken")
    private String ssoToken;
    @ApiModelProperty("系统编号")
    private String systemCode;
}
