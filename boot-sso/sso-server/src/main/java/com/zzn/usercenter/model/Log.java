package com.zzn.usercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月20日
 */
@Data
@ApiModel(value = "日志表")
public class Log extends BaseModel implements Serializable {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("操作人")
    private String operateName;
    @ApiModelProperty("请求地址")
    private String requestUrl;
    @ApiModelProperty("请求主机ip")
    private String remoteHost;
    @ApiModelProperty("系统编码")
    private String systemCode;
    @ApiModelProperty("请求类型")
    private String method;
    @ApiModelProperty("重定向地址")
    private String redirectUrl;
    @ApiModelProperty("删除标记，1为正常，0为删除")
    private Integer isEnabled;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;

}