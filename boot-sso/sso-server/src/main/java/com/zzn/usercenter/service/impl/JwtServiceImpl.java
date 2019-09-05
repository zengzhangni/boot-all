package com.zzn.usercenter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.zzn.usercenter.config.jwt.JwtProperties;
import com.zzn.usercenter.service.JwtService;
import com.zzn.usercenter.utils.jwt.JWTHelper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/8/8
 */
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Resource
    private JwtProperties properties;

    /**
     * @param map 创建token信息
     * @return
     */
    @Override
    public String generateToken(Map<String, Object> map) {
        String token = null;
        try {
            token = JWTHelper.generateToken(map, properties.getPrivateKey(), properties.getExpirationTime());
        } catch (Exception e) {
            log.error("创建sso_token失败!");
        }
        return token;
    }


    /**
     * @param token token
     * @return
     */
    @Override
    public Map<String, Object> getInfoFromToken(String token) {
        Map<String, Object> info = new HashMap<>(1);

        try {
            info = JWTHelper.getInfoFromToken(token, properties.getPublicKey());
        } catch (Exception e) {
            System.out.println(e);
            log.error("解析token失败,token错误或已失效");
            info.put("err_message", "解析token失败");
        }
        return info;
    }


}
