package com.zzn.push.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@SuppressWarnings("serial")
@ApiModel(value = "PUSH记录")
public class PushHistory implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("模板编码")
    private String templateCode;
    @ApiModelProperty("平台编码")
    private String platformCode;
    @ApiModelProperty("会员uuid")
    private String memberUuid;
    @ApiModelProperty("设备token")
    private String deviceToken;
    @ApiModelProperty("1--android系统 2--ios系统")
    private Integer deviceType;
    @ApiModelProperty("消息类型(0--服务通知  1--招募通知 2--活动通知)")
    private Integer msgType;
    @ApiModelProperty("通知栏通知信息")
    private String pushTicker;
    @ApiModelProperty("通知标题")
    private String pushTitle;
    @ApiModelProperty("通知文字描述")
    private String pushText;
    @ApiModelProperty("自定义内容")
    private String custom;
    @ApiModelProperty("跳转类型(0---app原生  1---html5)")
    private String forwardType;
    @ApiModelProperty("跳转页面url")
    private String forwardUrl;
    @ApiModelProperty("读取状态 0--未读取 1--已读取 2--删除")
    private String readStatus;
    @ApiModelProperty("创建时间")
    private java.util.Date createTime;
    @ApiModelProperty("更新时间")
    private java.util.Date updateTime;

}