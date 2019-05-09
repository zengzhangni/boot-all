package com.zzn.grou;

import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableSwagger2Doc
@SpringBootApplication
public class SwaggerGrouApp {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerGrouApp.class, args);
    }

}
