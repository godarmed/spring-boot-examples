package com.gzz;

import javax.annotation.PostConstruct;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	private RedissonClient redissonClient;

	@PostConstruct
	public void set() {
		RBucket<String> keyObj = redissonClient.getBucket("gzz");
		keyObj.set("gzz_gzz@163.com");
		log.info(keyObj.get());
	}
}
