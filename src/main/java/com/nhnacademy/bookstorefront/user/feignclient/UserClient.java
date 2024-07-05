package com.nhnacademy.bookstorefront.user.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;

@FeignClient(name = "user-feign-client", url = "http://localhost:8090", configuration = FeignClientConfig.class)
public interface UserClient {

	@GetMapping("/api/users/self")
	void getMyUserInfo();

	@PatchMapping("/api/users/dormant")
	ResponseEntity<Void> dormantUser();
}
