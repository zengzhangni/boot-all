

package com.zzn.usercenter.utils.jwt;

import io.jsonwebtoken.*;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/3/21
 */
public class JWTHelper {
    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

    /**
     * 密钥加密token
     *
     * @param priKey
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(Map<String, Object> map, String priKey, int expire) throws Exception {
        JwtBuilder builder = Jwts.builder();
        if (map != null) {
            builder.setClaims(map);
        }
        return builder
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(RsaKeyHelper.toBytes(priKey)))
                .compact();
    }


    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, String pubKey) throws Exception {
        byte[] bytes = RsaKeyHelper.toBytes(pubKey);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(bytes)).parseClaimsJws(token);
        return claimsJws;
    }


    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getInfoFromToken(String token, String pubKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        body.remove("exp");
        return body;
    }

    public static void main(String[] args) throws Exception {
        Map map = new HashMap();
        map.put("loginName", "张三");
        String p = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJD3jdu5wBQmX9LuDtjgCA5Bu+KZvQudDwD10najAVPP44IvRLAdRfm7aiH+UMwUECZr7BKxx6WrxQFEoAEY93XSZZKhNkvQkncyu7IOU+11REZVAIFzJmaRWmsd7Z4bhkOLwBkg7l08Wd/V79YaRT2M98/kc4ZLARH2s9ewb9l1AgMBAAECgYAoxOYpWDjzscMbMAYO6hp53/S2CqSG4pm4nGmfIcKazoleMGrx2Jm4XQPBIaOZcT8Ffy56J3NNXVxfOeyBwoK9PCxi8Y+pUhI41h/3oCgLqgfVuziqhJFUkq8B3GMcThU0+91OnvsYaucXtLqkb99+kG2y5BAdZqdrIb7qgH5S1QJBANLqGMxLBKuY+E88sbRLyh2+k3Ygl6heyH49rQpwM6nLDvxLzjLdVctu84LcjMbS5tMpkxujRokW8y6fyNDgYrsCQQCv9JsZH2hJ8Pf81WJwSZ3HLJYI8Ny7i4BLia9uVBy+L8e4Wu/2cidflxc33FYxywERx2nGut3PmSykIZxnLmmPAkEA0GanIhVuA/AeSoz6OeA5UbKhGjOSJreg+7wNYycCpV8S65Arqm6w+1YiHse2Ai8aDMQjq0dlxsd6ejM/xcQxSQJAVazuBGqVlkNeDN8+AlzmFYBxQcM+R5eijaVqJXeRU03imrK02zQ0Vcg1Bv8jfTVSvPxzVc/Rh28XHIg9JsSjowJBAKF9Q5/C16wpFNE4eNAd+NnlOC6tSHHiqDtlKzZ7fr1iBN1jHG01qYaH+LDX262oSGbkdSv8aeNhk0qj1Gmhae0=";
        Integer i = 3600;
        String token = generateToken(map, p, i);

        Map<String, Object> infoFromToken = getInfoFromToken(token, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQ943bucAUJl/S7g7Y4AgOQbvimb0LnQ8A9dJ2owFTz+OCL0SwHUX5u2oh/lDMFBAma+wSscelq8UBRKABGPd10mWSoTZL0JJ3MruyDlPtdURGVQCBcyZmkVprHe2eG4ZDi8AZIO5dPFnf1e/WGkU9jPfP5HOGSwER9rPXsG/ZdQIDAQAB");
        System.out.println(infoFromToken);


    }
}