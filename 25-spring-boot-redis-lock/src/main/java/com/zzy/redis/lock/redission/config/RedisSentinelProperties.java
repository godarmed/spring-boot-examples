package com.zzy.redis.lock.redission.config;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname RedisSentinelProperties
 * @Description TODO
 * @Date 2020/7/29 16:22
 * @Created by Zzy
 */
@Data
@ToString
public class RedisSentinelProperties {

    /**
     * 哨兵master 名称
     */
    private String master;

    /**
     * 哨兵节点
     */
    private String nodes;

    /**
     * 哨兵配置
     */
    private boolean masterOnlyWrite;

    /**
     *
     */
    private int failMax;
}
