package com.zzn.usercenter.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengzhangni
 * @date 2019/3/21
 */
@ConfigurationProperties(prefix = "jwt.params")
@Component
@Data
public class JwtProperties {
    /**
     * token的过期时间
     */
    private Integer expirationTime;
    /**
     * 密钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;
}