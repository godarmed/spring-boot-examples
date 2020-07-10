package com.zzy.redis.lock.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname ThreadPoolConfig
 * @Description TODO
 * @Date 2020/7/10 14:46
 * @Created by Zzy
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private int corePoolSize = 15;

    private int maxPoolSize = 20;

    @Bean
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //配置核心线程数
        executor.setCorePoolSize(this.corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(this.maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(9999);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");
        executor.setKeepAliveSeconds(60);
        // 增加 TaskDecorator 属性的配置
        //executor.setTaskDecorator(new ContextDecorator());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }

}
