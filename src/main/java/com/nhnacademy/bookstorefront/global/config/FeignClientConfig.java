package com.nhnacademy.bookstorefront.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Client;
import feign.okhttp.OkHttpClient;

@Configuration
public class FeignClientConfig {

    @Bean
    public Client okHttpClient() {
        return new OkHttpClient();
    }
}