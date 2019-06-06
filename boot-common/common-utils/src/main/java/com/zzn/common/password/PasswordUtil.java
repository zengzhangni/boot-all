package com.zzn.common.password;

import java.util.Random;

/**
 * @author zengzhangni
 * @date 2019/6/6
 */
public class PasswordUtil {

    private final static String[] WORD = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "j", "k", "m", "n",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G",
            "H", "J", "K", "M", "N",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    private final static String[] NUM = {
            "2", "3", "4", "5", "6", "7", "8", "9"
    };

    private static String randomPassword() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random(System.currentTimeMillis());
        boolean flag = false;
        //输出几位密码长度  这里是有可能8 ，9 ，10 位
        int length = random.nextInt(3) + 8;
        for (int i = 0; i < length; i++) {
            if (flag) {
                stringBuffer.append(NUM[random.nextInt(NUM.length)]);
            } else {
                stringBuffer.append(WORD[random.nextInt(WORD.length)]);
            }
            flag = !flag;
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(randomPassword());
        Thread.sleep(100);
        System.out.println(randomPassword());
        Thread.sleep(100);
        System.out.println(randomPassword());
    }
}
