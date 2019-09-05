package com.zzn.usercenter.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCentreVo {

    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("用户账号")
    private String loginName;
    @ApiModelProperty("密码")
    private String loginPassword;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("性别 0-男 1-女")
    private Integer gender;
    @ApiModelProperty("头像")
    private String userImgUrl;
    @ApiModelProperty("系统编码")
    private String systemCode;
    @ApiModelProperty("系统用户编码")
    private String systemUserCode;

}
