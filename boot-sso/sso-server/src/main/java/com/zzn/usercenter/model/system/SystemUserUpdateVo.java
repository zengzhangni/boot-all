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
public class SystemUserUpdateVo implements Serializable {

    @ApiModelProperty("用户账号")
    @NotBlank(message = "用户账号不能为null")
    private String loginName;
    @ApiModelProperty("新用户账号")
    private String newLoginName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("新密码")
    private String newPassword;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("系统编码")
    private String systemCode;

}
