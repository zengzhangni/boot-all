/**
 *
 */
package com.zzn.generator2.utils;


import com.zzn.generator2.config.PropertyHolder;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * 数据库连接工厂，单列
 */
public class ConnectionFactory {

    private static ConnectionFactory instance = new ConnectionFactory();

    public static ConnectionFactory getInstance() {
        return instance;
    }

    /**
     * 构造方法
     */
    private ConnectionFactory() {
        super();
    }

    /**
     * 获取数据库驱动类
     * @return Driver 数据库驱动
     * @author yangziran
     */
    private Driver getDriver(String driverClass) {
        Driver driver;
        try {
            Class<?> clazz = Class.forName(driverClass);
            driver = (Driver) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(MessageFormat.format("获取JDBC Driver错误", new Object[]{}), e);
        }

        return driver;
    }

    /**
     * 获取数据库连接
     * @return Connection 数据库连接
     * @throws SQLException
     * @author yangziran
     */
    public Connection getConnection(String driverClass, String url, String userName, String password) throws SQLException {
        Driver driver = getDriver(driverClass);

        Properties props = new Properties();
        props.setProperty("useSSL", "false");
        // 设置可以获取remarks信息
        props.setProperty("remarks", "true");
        // 设置可以获取tables remarks信息
        props.setProperty("useInformationSchema", "true");
        // 设置连接编码
        props.setProperty("characterEncoding", "utf8");
        // 当数据库连接异常中断时，是否自动重新连接？
        props.setProperty("autoReconnect", "true");
        // 自动重连成功后，连接是否设置为只读？
        props.setProperty("failOverReadOnly", "false");

        props.setProperty("user", userName);

        props.setProperty("password", password);

        Connection conn = driver.connect(url, props);

        if (conn == null) {
            throw new SQLException(MessageFormat.format("无法连接到数据库（可能是驱动/URL错误）", new Object[]{}));
        }

        return conn;
    }

    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static Connection getConnection() throws SQLException {
        String url = PropertyHolder.getJDBCProperty("url");
        String user = PropertyHolder.getJDBCProperty("username");
        String password = PropertyHolder.getJDBCProperty("password");
        String driver = PropertyHolder.getJDBCProperty("driverClass");

        return ConnectionFactory.getInstance().getConnection(driver, url, user, password);

    }
}