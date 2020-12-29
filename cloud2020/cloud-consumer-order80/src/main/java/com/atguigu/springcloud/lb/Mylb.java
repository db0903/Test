package com.atguigu.springcloud.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author db
 * @date 2020/12/25 - 13:48
 */
@Component //让容器扫描到这个类
public class Mylb implements LoadBalancer {

    private AtomicInteger atomicInteger  = new AtomicInteger(0);

    public final int getAndIncrement(){  //自定义自选锁
        int current;
        int next;  //代表第几次访问
        do{
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 :current + 1; //2147483647 整形的最大值
        }while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("**************第几次访问next="+next);
        return next;
    }

    //负载均衡算法：rest接口的第几次请求数 % 服务器的集群总数量 = 实际调用服务器位置的下标，每次服务器重新启动后rest接口计数从1开始
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
