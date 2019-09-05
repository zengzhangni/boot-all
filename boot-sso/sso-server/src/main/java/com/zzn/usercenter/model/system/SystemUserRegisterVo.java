package com.zzn.usercenter.model.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zengzhangni
 * @date 2019/8/15
 */
@Data
public class SystemUserRegisterVo implements Serializable {

    @ApiModelProperty("用户账号")
    private String loginName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("系统编码")
    @NotBlank(message = "系统编码不能为空")
    private String systemCode;

}
