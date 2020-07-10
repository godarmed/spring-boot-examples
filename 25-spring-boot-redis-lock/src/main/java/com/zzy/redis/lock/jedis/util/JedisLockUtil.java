package com.zzy.redis.lock.jedis.util;

import com.zzy.redis.lock.common.config.RedisConfig;
import com.zzy.redis.lock.common.config.RedisConfigProperties;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @Classname RedisUtil
 * @Description TODO
 * @Date 2020/7/10 11:58
 * @Created by Zzy
 */
public class JedisLockUtil {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final Long RELEASE_SUCCESS = 1L;
    private Jedis jedis;
    private RedisConfig redisConfig;
    private RedisConfigProperties properties;

    public JedisLockUtil() throws Exception {
        this.redisConfig = (RedisConfig)SpringUtils.getBean(RedisConfig.class);
        if (this.redisConfig != null) {
            this.properties = this.redisConfig.getRedisConfigProperties();
            this.jedis = this.redisConfig.redisPoolFactory().getResource();
        } else {
            throw new Exception("jedis init error");
        }
    }

    /**
     * 获取分布式锁(加锁代码)
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean getDistributedLock(String lockKey, String requestId, int expireTime) {

        String result = this.jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 循环获取分布式锁(加锁代码)
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @param waitTime 等待时间
     * @return 是否获取成功
     */
    public boolean getDistributedLock(String lockKey, String requestId, int expireTime, int waitTime) throws Exception {
        Long getTimeout = System.currentTimeMillis() + waitTime;
        String acquired = null;

        do {
            if (System.currentTimeMillis() > getTimeout) {
                return false;
            }

            acquired = this.jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(acquired)) {
                return true;
            }

            Thread.sleep(10L);
        } while(System.currentTimeMillis() <= getTimeout);

        return false;
    }

    /**
     * 释放分布式锁(解锁代码)
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {

        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        Object result = this.jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }
}

