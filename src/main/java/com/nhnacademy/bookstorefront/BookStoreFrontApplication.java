package com.nhnacademy.bookstorefront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableAspectJAutoProxy
@ConfigurationPropertiesScan
public class BookStoreFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreFrontApplication.class, args);
	}

}
