package com.zzy.redis.lock.redission.utils;

import com.zzy.redis.lock.common.util.SpringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @Classname RedissionLockUtil
 * @Description TODO
 * @Date 2020/7/29 16:59
 * @Created by Zzy
 */

public class RedissonLockUtil {

    private static Long DEFAULT_WAITTIME = 60L;

    private RedissonClient client;

    public void setRedissonClient(RedissonClient redissonClient) {
        this.client = redissonClient;
    }

    public RedissonLockUtil() {
        if(this.client == null){
            this.client = (RedissonClient) SpringUtils.getBean("CustomRedissonClient");
        }
    }

    public RLock lock(String lockKey) {
        if (client == null) {
            return null;
        } else {
            RLock lock = client.getLock(lockKey);

            try {
                if (lock.tryLock(DEFAULT_WAITTIME, TimeUnit.SECONDS)) {
                    return lock;
                }
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            return null;
        }
    }

    public RLock lock(String lockKey, Long waitTime) {
        if (client == null) {
            return null;
        } else {
            RLock lock = client.getLock(lockKey);

            try {
                if (lock.tryLock(waitTime, TimeUnit.SECONDS)) {
                    return lock;
                }
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            return null;
        }
    }

    public RLock lock(String lockKey, Long waitTime, TimeUnit timeUnit) {
        if (client == null) {
            return null;
        } else {
            RLock lock = client.getLock(lockKey);

            try {
                if (lock.tryLock(waitTime, timeUnit)) {
                    return lock;
                }
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            return null;
        }
    }


    public void release(RLock lock) {
        if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }

    }

    public boolean release(String lockKey) {
        RLock lock = client.getLock(lockKey);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }

        return true;
    }

}

