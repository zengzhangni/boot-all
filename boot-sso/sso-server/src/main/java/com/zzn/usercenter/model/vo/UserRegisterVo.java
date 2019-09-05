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
public class UserRegisterVo implements Serializable {

    @ApiModelProperty("用户账号")
    @NotBlank(message = "账号不能为空")
    private String registerName;
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String registerPassword;
    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("系统编码")
    private String systemCode;


}
