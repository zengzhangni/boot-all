package com.zzn.usercenter.utils.sso;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author GaoWei
 * @describe MD5简单加密工具类
 * @time 2017/12/18,13:43
 */
public class Md5Util {
    /**
     * /md5密码的盐
     */
    public static final String MD5_CNBBX = "cdhnf";
    /**
     * 拼接符
     */
    private final static String EQ = "=";


    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String plainText) {
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        // 16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);
        StringBuilder builder = new StringBuilder(md5code);
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            builder.append("0" + md5code);
        }
        return builder.toString();
    }

    public static String md5(Map<String, String> param) {
        //字典排序
        String paramStr = Md5Util.getParamStr(param);
        //加密
        return Md5Util.md5(paramStr);

    }

    /**
     * 将map参数拼接 按字典顺序排序
     *
     * @param param
     * @return
     */
    public static String getParamStr(Map<String, String> param) {
        List<String> list = new ArrayList<>();
        param.entrySet().stream()
                .forEach(m -> {
                    if (!StringUtils.isEmpty(m.getValue())) {
                        list.add(new StringBuilder().append(m.getKey()).append(EQ).append(m.getValue()).toString());
                    }
                });
        String[] arrayToSort = list.toArray(new String[list.size()]);
        //字典排序方式
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        Arrays.stream(arrayToSort).forEach(s -> sb.append(s).append("&"));
        return sb.substring(0, sb.length() - 1);
    }



    public static Boolean decoding(String signature, Map<String, String> param, String systemKey) {
        String paramStr = getParamStr(param);
        String md5 = md5(paramStr + systemKey);
        return md5.equals(signature);
    }

}
