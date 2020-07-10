package com.zzy.redis.lock.common.test;

import com.zzy.redis.lock.common.util.RedisUtil;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Classname RedisTestController
 * @Description TODO
 * @Date 2020/7/10 11:30
 * @Created by Zzy
 */
@RequestMapping("/redis")
@RestController
public class RedisTestController {

    private static int ExpireTime = 60;   // redis中存储的过期时间60s

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/set")
    public boolean redisset(String key, String value){
        TestEntity testEntity =new TestEntity();
        testEntity.setId(1L);
        testEntity.setGuid(String.valueOf(1));
        testEntity.setName("zhangsan");
        testEntity.setAge(20);
        testEntity.setCreateTime(new Date());

        return redisUtil.set(key,testEntity,ExpireTime);
    }

    @RequestMapping("/get")
    public Object redisget(String key){
        return redisUtil.get(key);
    }

    @RequestMapping("/expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }

    @Data
    static class TestEntity{
        private Long id;
        private String guid;
        private String name;
        private Integer age;
        private Date createTime;
    }
}
