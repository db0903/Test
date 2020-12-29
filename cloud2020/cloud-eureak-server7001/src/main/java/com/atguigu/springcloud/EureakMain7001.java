package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author db
 * @date 2020/12/7 - 21:58
 */
@SpringBootApplication
@EnableEurekaServer
public class EureakMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EureakMain7001.class,args);
    }
}
