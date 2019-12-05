package com.jlkj.web.shopnew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"com.jlkj.web.shopnew"})
@ImportResource(locations = { "classpath*:spring-config.xml", "classpath*:spring-common.xml"})
@EnableTransactionManagement
public class SpringbooRabbitMqAppliaction {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringbooRabbitMqAppliaction.class, args);
	}

}
