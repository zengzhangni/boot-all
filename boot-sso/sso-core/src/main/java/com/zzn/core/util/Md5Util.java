package com.zzn.core.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/8/20
 */
public class Md5Util {


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


}
