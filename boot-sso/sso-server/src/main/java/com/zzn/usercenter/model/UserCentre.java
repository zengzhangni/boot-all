package com.zzn.usercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value = "用户基本信息表")
public class UserCentre extends BaseModel implements Serializable {

    @ApiModelProperty("ID")
    private Integer id;
    @ApiModelProperty("用户code")
    private String userCode;
    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("用户账号")
    private String loginName;
    @ApiModelProperty("密码")
    private String loginPassword;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("性别 1-男 0-女")
    private Integer gender;
    @ApiModelProperty("头像")
    private String userImgUrl;
    @ApiModelProperty("系统来源")
    private String systemSource;
    @ApiModelProperty("删除标记，1为正常，0为删除")
    private Integer isEnabled;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date createTime;
    @ApiModelProperty("更新时间")
    private java.util.Date updateTime;

}