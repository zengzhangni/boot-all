package com.zzn.common.jwt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/6/14
 */
public class Jwt {
    public static void main(String[] args) throws Exception {
        Map map = new HashMap();
        map.put("memberUuid", "123456789");
        map.put("phone", "159847625");
        String priKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJD3jdu5wBQmX9LuDtjgCA5Bu+KZvQudDwD10najAVPP44IvRLAdRfm7aiH+UMwUECZr7BKxx6WrxQFEoAEY93XSZZKhNkvQkncyu7IOU+11REZVAIFzJmaRWmsd7Z4bhkOLwBkg7l08Wd/V79YaRT2M98/kc4ZLARH2s9ewb9l1AgMBAAECgYAoxOYpWDjzscMbMAYO6hp53/S2CqSG4pm4nGmfIcKazoleMGrx2Jm4XQPBIaOZcT8Ffy56J3NNXVxfOeyBwoK9PCxi8Y+pUhI41h/3oCgLqgfVuziqhJFUkq8B3GMcThU0+91OnvsYaucXtLqkb99+kG2y5BAdZqdrIb7qgH5S1QJBANLqGMxLBKuY+E88sbRLyh2+k3Ygl6heyH49rQpwM6nLDvxLzjLdVctu84LcjMbS5tMpkxujRokW8y6fyNDgYrsCQQCv9JsZH2hJ8Pf81WJwSZ3HLJYI8Ny7i4BLia9uVBy+L8e4Wu/2cidflxc33FYxywERx2nGut3PmSykIZxnLmmPAkEA0GanIhVuA/AeSoz6OeA5UbKhGjOSJreg+7wNYycCpV8S65Arqm6w+1YiHse2Ai8aDMQjq0dlxsd6ejM/xcQxSQJAVazuBGqVlkNeDN8+AlzmFYBxQcM+R5eijaVqJXeRU03imrK02zQ0Vcg1Bv8jfTVSvPxzVc/Rh28XHIg9JsSjowJBAKF9Q5/C16wpFNE4eNAd+NnlOC6tSHHiqDtlKzZ7fr1iBN1jHG01qYaH+LDX262oSGbkdSv8aeNhk0qj1Gmhae0=";
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQ943bucAUJl/S7g7Y4AgOQbvimb0LnQ8A9dJ2owFTz+OCL0SwHUX5u2oh/lDMFBAma+wSscelq8UBRKABGPd10mWSoTZL0JJ3MruyDlPtdURGVQCBcyZmkVprHe2eG4ZDi8AZIO5dPFnf1e/WGkU9jPfP5HOGSwER9rPXsG/ZdQIDAQAB";
        String token = JWTHelper.generateToken(map, priKey, 7200);

        System.out.println("token:"+token);
        Map infoFromToken = JWTHelper.getInfoFromToken(token, pubKey);
        System.out.println("token解析:"+infoFromToken);

    }
}
