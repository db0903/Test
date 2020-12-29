package com.atguigu.springcloud.util;

import java.util.UUID;

/**
 * @author db
 * @date 2020/12/21 - 16:57
 */
public class StringUtils {

    /**
     * 生成32位编码
     * @return string
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }
}
