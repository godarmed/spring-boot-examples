package org.activiti.examples.config;

import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Classname Activiti
 * @Description TODO
 * @Date 2020/6/15 9:50
 * @Created by Zzy
 */
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {

    @Qualifier("dataSource")
    private DataSource dataSource;

    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager;

    //注入数据源和事务管理器
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(SpringAsyncExecutor springAsyncExecutor) throws IOException {
        return this.baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
    }

}

