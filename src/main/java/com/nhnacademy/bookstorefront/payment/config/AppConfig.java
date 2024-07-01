package com.nhnacademy.bookstorefront.payment.config;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
			.additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
			.build();
	}
}