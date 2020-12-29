package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author db
 * @date 2020/12/28 - 14:02
 */

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "-----------PaymentFallbackService fall back-paymentInfo_OK (┬＿┬)";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "-----------PaymentFallbackService fall back-paymentInfo_TimeOut (┬＿┬)\"";
    }
}
