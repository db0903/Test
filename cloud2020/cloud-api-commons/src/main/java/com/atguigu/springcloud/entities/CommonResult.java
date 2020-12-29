package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author db
 * @date 2020/12/5 - 11:54
 */
@Data
@AllArgsConstructor //全参
@NoArgsConstructor //空参
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code,String message){
        this(code,message, null);
    }
}
