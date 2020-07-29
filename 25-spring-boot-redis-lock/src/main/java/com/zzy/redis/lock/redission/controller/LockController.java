package com.zzy.redis.lock.redission.controller;

import com.zzy.redis.lock.jedis.util.SpringUtils;
import com.zzy.redis.lock.redission.utils.RedissonLockUtil;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Classname LockController
 * @Description TODO
 * @Date 2020/7/29 16:13
 * @Created by Zzy
 */
@Log4j2
@RequestMapping("/redisson")
@RestController
public class LockController {

    @RequestMapping("/testLock")
    public void testLock(int threadNum){
        for (int i = 0; i < threadNum; i++) {
            SpringUtils.getBean(com.zzy.redis.lock.jedis.controller.JedisLockController.class).getLock();
        }
    }

    @Async("asyncServiceExecutor")
    public void getLock(){
        RedissonLockUtil redissonLockUtil = null;
        String lockName = "OAUTH2_TOKEN_ZZYZZY";
        String requestId = String.valueOf(Thread.currentThread().getId());
        int expireTime = 60;
        boolean isGetLock = false;
        RLock rLock = null;
        try {
            redissonLockUtil = new RedissonLockUtil();
            if ((rLock = redissonLockUtil.lock(lockName)) != null) {
                log.info("线程:name:[{}],id:[{}]成功获取到锁",Thread.currentThread().getName(),Thread.currentThread().getId());
                log.info("线程:name:[{}],id:[{}]开始执行业务代码",Thread.currentThread().getName(),Thread.currentThread().getId());
                TimeUnit.SECONDS.sleep(10);
                log.info("线程:name:[{}],id:[{}]结束执行业务代码",Thread.currentThread().getName(),Thread.currentThread().getId());
            } else {
                log.info("线程:name:[{}],id:[{}]未获取到锁",Thread.currentThread().getName(),Thread.currentThread().getId());
            }
        } catch (Exception e) {
            log.error("线程:name:[{}],id:[{}],发生业务异常[{}]",Thread.currentThread().getName(),Thread.currentThread().getId(),e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(rLock!=null ){
                    redissonLockUtil.release(rLock);
                    log.info("线程:name:[{}],id:[{}]释放锁",Thread.currentThread().getName(),Thread.currentThread().getId());
                }
            } catch (Exception e) {
                log.error("线程:name:[{}],id:[{}]释放锁失败,失败原因[{}]",Thread.currentThread().getName(),Thread.currentThread().getId(),e.getMessage());
                e.printStackTrace();
            }
        }

    }
}