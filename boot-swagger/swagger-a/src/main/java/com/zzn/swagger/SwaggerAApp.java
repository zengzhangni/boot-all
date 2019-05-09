package com.zzn.swagger;

import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableSwagger2Doc
@SpringBootApplication
public class SwaggerAApp {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerAApp.class, args);
    }

}
