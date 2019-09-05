package com.zzn.push.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@SuppressWarnings("serial")
@ApiModel(value = "用户友盟信息表")
public class MemberUmeng implements Serializable {

    @ApiModelProperty("主键ID")
    private Integer id;
    @ApiModelProperty("平台编码")
    private String platformCode;
    @ApiModelProperty("会员uuid")
    private String memberUuid;
    @ApiModelProperty("设备token")
    private String deviceToken;
    @ApiModelProperty("设备系统，1--android系统 2--ios系统")
    private Integer deviceType;
    @ApiModelProperty("软删除标记，1为正常，0为删除")
    private Byte isEnabled;
    @ApiModelProperty("创建时间")
    private java.util.Date createTime;
    @ApiModelProperty("更新时间")
    private java.util.Date updateTime;

}