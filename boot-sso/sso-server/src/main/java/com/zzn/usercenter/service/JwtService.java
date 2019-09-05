package com.zzn.usercenter.service;

import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/8/8
 */
public interface JwtService {


    /**
     * @param map 创建token信息
     * @return
     */
    String generateToken(Map<String, Object> map);


    /**
     * @param token token
     * @return
     */
    Map<String, Object> getInfoFromToken(String token);


}
