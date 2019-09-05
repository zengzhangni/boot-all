package com.zzn.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月22日
 */
@Data
@ApiModel(value = "菜单表")
public class Menu extends BaseModel implements Serializable {

    @ApiModelProperty("父id")
    private Integer pId;
    @ApiModelProperty("菜单名字")
    private String name;
    @ApiModelProperty("首页地址")
    private String indexUrl;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty(value = "子菜单", hidden = true)
    private List<Menu> childs;
    @ApiModelProperty("删除标记，1为正常，0为删除")
    private Byte isEnabled;
    @ApiModelProperty("创建时间")
    private java.util.Date createTime;
    @ApiModelProperty("更新时间")
    private java.util.Date updateTime;

}