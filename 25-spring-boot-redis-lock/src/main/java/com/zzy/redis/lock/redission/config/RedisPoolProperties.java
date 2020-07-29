package com.zzy.redis.lock.redission.config;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname RedisPoolProperties
 * @Description TODO
 * @Date 2020/7/29 16:21
 * @Created by Zzy
 */
@Data
@ToString
public class RedisPoolProperties {

    private int maxIdle;

    private int minIdle;

    private int maxActive;

    private int maxWait;

    private int connTimeout;

    private int soTimeout;

    /**
     * 池大小
     */
    private  int size;

}