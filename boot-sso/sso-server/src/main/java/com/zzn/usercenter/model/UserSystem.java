package com.zzn.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value = "用户系统表")
public class UserSystem extends BaseModel implements Serializable {

    @ApiModelProperty("ID")
    private Integer id;
    @ApiModelProperty("用户登录名")
    private String loginName;
    @ApiModelProperty("用户编码")
    private String userCode;
    @ApiModelProperty("系统编码")
    private String systemCode;
    @ApiModelProperty("系统用户编码")
    private String systemUserCode;
    @ApiModelProperty("删除标记，1为正常，0为删除")
    private Byte isEnabled;
    @ApiModelProperty("创建时间")
    private java.util.Date createTime;
    @ApiModelProperty("更新时间")
    private java.util.Date updateTime;

}