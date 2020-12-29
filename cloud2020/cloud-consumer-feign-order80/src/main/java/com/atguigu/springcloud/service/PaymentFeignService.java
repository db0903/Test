package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author db
 * @date 2020/12/25 - 14:51
 */
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {

    @GetMapping(value = "/payment/getById/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id);

    //暂停几秒钟
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout();
}
