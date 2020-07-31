package com.zzy.redis.lock.jedis.controller;

import com.zzy.redis.lock.jedis.util.JedisLockUtil;
import com.zzy.redis.lock.common.util.SpringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Classname JedisLockController
 * @Description TODO
 * @Date 2020/7/10 12:15
 * @Created by Zzy
 */
@Log4j2
@RequestMapping("/redis")
@RestController
public class JedisLockController {

    @RequestMapping("/testLock")
    public void testLock(int threadNum){
        for (int i = 0; i < threadNum; i++) {
            SpringUtils.getBean(JedisLockController.class).getLock();
        }
    }

    @Async
    public void getLock(){
        JedisLockUtil jedisLockUtil = null;
        String lockName = "OAUTH2_TOKEN_ZZYZZY";
        String requestId = String.valueOf(Thread.currentThread().getId());
        int expireTime = 60;
        boolean isGetLock = false;
        try{
            jedisLockUtil = new JedisLockUtil();
            if (isGetLock = jedisLockUtil.getDistributedLock(lockName, requestId, expireTime, 10000)) {
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
                if(isGetLock){
                    jedisLockUtil.releaseDistributedLock(lockName,requestId);
                    jedisLockUtil.close();
                    log.info("线程:name:[{}],id:[{}]释放锁",Thread.currentThread().getName(),Thread.currentThread().getId());
                }
            } catch (Exception e) {
                log.error("线程:name:[{}],id:[{}]释放锁失败,失败原因[{}]",Thread.currentThread().getName(),Thread.currentThread().getId(),e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
