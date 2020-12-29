package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author db
 * @date 2020/12/6 - 16:13
 */
@Configuration
public class ApplicationContextConfig {
    //注解的方式依赖注入
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
        //applicationContext.xml <bean id="" class="">
    }

}
