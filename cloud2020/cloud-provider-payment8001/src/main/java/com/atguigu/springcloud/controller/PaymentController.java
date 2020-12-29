package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author db
 * @date 2020/12/5 - 12:26
 */
@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;



    @GetMapping(value = "/payment/getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id) {
        System.out.println(id);
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果success：" + payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功,servrtport:"+serverPort, payment);
        }
        return new CommonResult(444, "无对应记录,查询ID:" + id, null);
    }

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*****插入结果success:" + result);
        if (result > 0) {
            return new CommonResult(200, "插入成功,servrtport:"+serverPort, result);
        }
        return new CommonResult(444, "插入失败", null);
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("**********service:"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    // 负载均衡
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    //暂停几秒钟
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
