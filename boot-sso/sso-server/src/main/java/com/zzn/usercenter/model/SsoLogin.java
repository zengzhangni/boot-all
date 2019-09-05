package com.zzn.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "login对象")
public class SsoLogin implements Serializable {

    @ApiModelProperty("资源地址")
    private String redirectUrl;
    @ApiModelProperty("登录地址")
    private String loginUrl;
    @ApiModelProperty("ssoToken")
    private String ssoToken;
    @ApiModelProperty("系统编码")
    private String systemCode;
}