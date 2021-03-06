/**
 *
 */
package com.zzn.generator2.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 数据库属性设置
 */
public class PropertyHolder {

    // 获取到类加载器
    private static ClassLoader parent = Thread.currentThread().getContextClassLoader();
    /** 属性文件名称 */
    private static final String MYSQL = "mysql.properties";

    /**
     * 获取到JDBC属性
     * @return
     * @author yangziran
     */
    private static Properties getJDBCProperties() {

        // 将属性文件加载到流中
        InputStream inputStream = parent.getResourceAsStream(MYSQL);
        // 创建属性对象
        Properties properties = new Properties();
        try {
            // 加载文件流到属性对象中
            properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            // 关闭文件流
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    public static String getJDBCProperty(String name) {
        return getJDBCProperties().getProperty(name);
    }

}