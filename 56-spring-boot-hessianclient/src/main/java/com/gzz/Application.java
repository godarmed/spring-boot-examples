package com.gzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.gzz.sys.user.IHelloService;

/**
 * @author https://www.jianshu.com/u/3bd57d5f1074
 * @date 2019-12-24 14:50:00
 */
@SpringBootApplication
public class Application {
	@Bean
	public HessianProxyFactoryBean helloClient() {
		HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
		factory.setServiceUrl("http://localhost:8080/HelloService");
		factory.setServiceInterface(IHelloService.class);
		return factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
