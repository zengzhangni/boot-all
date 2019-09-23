package com.zzn.pay.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/9/11
 */
@Data
public class BaseVo implements Serializable {

    @NotBlank(message = "渠道id不能为空")
    @ApiModelProperty("渠道id(实现类名)")
    public String channelId;

    @ApiModelProperty("商户配置")
    @NotEmpty(message = "商户配置不能为空")
    public Map<String,String> merchantConfig;

    @ApiModelProperty("渠道配置")
    @NotEmpty(message = "渠道配置不能为空")
    public Map<String,String> channelConfig;

    @ApiModelProperty("扩展信息")
    private Map<String,String> extend;

}
