package com.zzn.boot.ui;

import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zengzhangni
 * @date 2020/6/24 16:57
 * @since v1.1.0
 */
@SpringBootApplication
public class GeberatorUIServer {

    public static void main(String[] args) {
        SpringApplication.run(GeberatorUIServer.class, args);
        GeneratorConfig config = GeneratorConfig.builder().jdbcUrl("jdbc:mysql:///catering_cloud")
                .userName("root")
                .password("123456")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                //数据库schema，POSTGRE_SQL,ORACLE,DB2类型的数据库需要指定
//                .schemaName("myBusiness")
                .basePackage("com.github.davidfantasy.mybatisplustools.example")
                .port(8068)
                .build();
        MybatisPlusToolsApplication.run(config);
    }
}
