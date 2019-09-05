package com.zzn.usercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value = "系统表")
public class System extends BaseModel implements Serializable {

    @ApiModelProperty("ID")
    private Integer id;
    @ApiModelProperty("系统编码")
    private String systemCode;
    @ApiModelProperty("系统名称")
    private String systemName;
    @ApiModelProperty("系统域名")
    private String systemDomain;
    @ApiModelProperty("系统首页地址")
    private String indexUrl;
    @ApiModelProperty("系统key")
    private String systemKey;
    @ApiModelProperty("删除标记，1为正常，0为删除")
    private Byte isEnabled;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private java.util.Date createTime;
    @ApiModelProperty("更新时间")
    private java.util.Date updateTime;


}