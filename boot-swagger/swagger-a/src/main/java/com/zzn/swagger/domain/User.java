package com.zzn.swagger.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户")
public class User {

    @ApiModelProperty("Id")
    private Integer id;
    @ApiModelProperty("姓名")
    private String userName;
    @ApiModelProperty("密码")
    private String passWord;
    @ApiModelProperty("性别")
    private Integer sex;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("金额")
    private Double je;
    @ApiModelProperty("不知道")
    private Boolean ds;
    @ApiModelProperty("生日")
    private Date sr;
}
