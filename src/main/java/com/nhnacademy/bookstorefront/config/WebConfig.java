package com.nhnacademy.bookstorefront.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/admin").setViewName("admin/admin-account");
		registry.addViewController("/user").setViewName("user/user-account");
		registry.addViewController("/product-details").setViewName("book/product-details");
		registry.addViewController("/get-book").setViewName("book/get-book");
	}
}
