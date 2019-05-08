package com.zzn.excel.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户")
public class User {

    @ApiModelProperty("id")
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
    @ApiModelProperty("是否拥有")
    private Boolean ds;
    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sr;

}
