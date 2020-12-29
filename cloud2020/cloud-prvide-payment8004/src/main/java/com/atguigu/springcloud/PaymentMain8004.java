package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author db
 * @date 2020/12/16 - 20:39
 */
@SpringBootApplication
@EnableDiscoveryClient   //该注解用于向使用consul或zookeeper 作为注册中心是的注册服务
public class PaymentMain8004 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8004.class,args);
    }
}
