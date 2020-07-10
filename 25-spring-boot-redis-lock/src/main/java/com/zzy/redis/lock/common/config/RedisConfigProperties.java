package com.zzy.redis.lock.common.config;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RedisConfigProperties
 * @Description TODO
 * @Date 2020/7/10 12:21
 * @Created by Zzy
 */
@Data
@ConfigurationProperties(
        prefix = "spring.redis"
)
@Configuration
public class RedisConfigProperties extends GenericObjectPoolConfig {
    private String host;
    private int port;
    private int timeout;
    private String password;
    private int database;
    private Long ttl = 0L;
    private String sessionKeyPrefix = "spring";
    private String cacheKeyPrefix = "";
    private boolean isCluster = false;
    private boolean testWhileIdle = true;
    private long minEvictableIdleTimeMillis = 60000L;
    private long timeBetweenEvictionRunsMillis = 30000L;
    private int numTestsPerEvictionRun = -1;
}
