package com.gzz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gzz.dao.CustomerDao;
import com.gzz.model.Customer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author https://www.jianshu.com/u/3bd57d5f1074
 * @date 2019-12-24 14:50:00
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private CustomerDao dao;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dao.deleteAll();

		dao.save(new Customer("1", "Alice", "Smith"));
		dao.save(new Customer("2", "Bob", "Smith"));

		for (Customer customer : dao.findAll()) {
			log.info(customer.toString());
		}

		log.info(dao.findByFirstName("Alice").toString());

		for (Customer customer : dao.findByLastName("Smith")) {
			log.info(customer.toString());
		}
	}

}
