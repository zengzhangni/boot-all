package com.zzn.pay;

import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@EnableSwagger2Doc
@EnableTransactionManagement
@ComponentScan("com.zzn")
//@EnableAsync
public class PaycenterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaycenterServiceApplication.class);
    }
}
