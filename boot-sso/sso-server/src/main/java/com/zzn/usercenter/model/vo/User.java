package com.zzn.usercenter.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zengzhangni
 * @date 2019/8/15
 */
@Data
public class User implements Serializable {

    @ApiModelProperty("用户账号")
    @NotBlank(message = "账号不能为空")
    private String loginName;
    @ApiModelProperty("用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty("重定向地址")
    private String redirectUrl;
    @ApiModelProperty("系统编码")
    private String systemCode;
    @ApiModelProperty("签名")
    private String signature;


}
