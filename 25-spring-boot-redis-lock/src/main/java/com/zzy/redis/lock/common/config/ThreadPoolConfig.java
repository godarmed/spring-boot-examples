package com.zzy.redis.lock.common.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
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
public class ThreadPoolConfig implements AsyncConfigurer {

//    private int corePoolSize = 15;
//
//    private int maxPoolSize = 20;
//
//    @Bean
//    public Executor asyncServiceExecutor() {
//        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
//
//        //配置核心线程数
//        executor.setCorePoolSize(this.corePoolSize);
//        //配置最大线程数
//        executor.setMaxPoolSize(this.maxPoolSize);
//        //配置队列大小
//        executor.setQueueCapacity(9999);
//        //配置线程池中的线程的名称前缀
//        executor.setThreadNamePrefix("async-service-");
//        executor.setKeepAliveSeconds(60);
//        // 增加 TaskDecorator 属性的配置
//        //executor.setTaskDecorator(new ContextDecorator());
//        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
//        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        //执行初始化
//        executor.initialize();
//        return executor;
//    }

    private Logger logger = LogManager.getLogger();

    //@Value("${thread.pool.corePoolSize:10}")
    private int corePoolSize = 10;

    //@Value("${thread.pool.maxPoolSize:20}")
    private int maxPoolSize = 20;

    //@Value("${thread.pool.keepAliveSeconds:4}")
    private int keepAliveSeconds = 4;

    //@Value("${thread.pool.queueCapacity:512}")
    private int queueCapacity = 5120;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setQueueCapacity(queueCapacity);
        executor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor exe) -> {
            logger.warn("当前任务线程池队列已满.");
        });
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex , Method method , Object... params) {
                logger.error("线程池执行任务发生未知异常.", ex);
            }
        };
    }

}
