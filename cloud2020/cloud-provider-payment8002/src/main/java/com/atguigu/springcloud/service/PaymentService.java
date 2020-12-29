package com.atguigu.springcloud.service;


import com.atguigu.springcloud.entities.Payment;

/**
 * @author db
 * @date 2020/12/5 - 12:20
 */
public interface PaymentService {
    public int create(Payment payment);
    public Payment getPaymentById(Long id);
}
