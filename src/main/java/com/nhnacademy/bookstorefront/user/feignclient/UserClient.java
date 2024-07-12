package com.nhnacademy.bookstorefront.user.feignclient;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;

@FeignClient(name = "user-feign-client", url = "http://localhost:8090", configuration = FeignClientConfig.class)
public interface UserClient {

	@GetMapping("/api/users/self")
	ResponseEntity<GetMyUserInfoResponse> getMyUserInfo();

	@PatchMapping("/api/users/dormant")
	ResponseEntity<Void> dormantUser();

	@GetMapping("/api/users/self/total-order-price")
	ResponseEntity<BigDecimal> getMyTotalOrderPrice();

	// TODO: 회원 정보 수정
}
