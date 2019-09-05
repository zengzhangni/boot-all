package com.zzn.push.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zengzhangni
 * @date 2019/3/27
 */
@ConfigurationProperties(prefix = "push.umeng")
@Component
@Data
public class UmengProperties {

    private List<String> paramList;
}
