package com.zzn.push.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value = "PUSH模版")
public class PushTemplate implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("平台编码")
    private String platformCode;
    @ApiModelProperty("模板名称")
    private String templateName;
    @ApiModelProperty("模板编码")
    private String templateCode;
    @ApiModelProperty("模板内容")
    private String templateContent;
    @ApiModelProperty("模板描述")
    private String templateDescription;
    @ApiModelProperty("推送标题")
    private String pushTitle;
    @ApiModelProperty("0--服务消息 1--项目消息  2--系统消息")
    private Integer pushType;
    @ApiModelProperty("软删除标记，1为正常，0为删除")
    private Byte isEnabled;
    @ApiModelProperty("创建时间")
    private java.util.Date createTime;
    @ApiModelProperty("更新时间")
    private java.util.Date updateTime;

}