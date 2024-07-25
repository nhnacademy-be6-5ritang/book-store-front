package com.nhnacademy.bookstorefront.user.feignclient;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.global.config.FeignClientConfig;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserInfoResponse;

@FeignClient(name = "user-feign-client", url = "http://localhost:8090", configuration = FeignClientConfig.class)
public interface UserClient {

	@GetMapping("/api/users/self")
	ResponseEntity<GetMyUserInfoResponse> getMyUserInfo();

	@PatchMapping("/api/users/withdraw")
	ResponseEntity<Void> withdrawUser();

	@GetMapping("/api/users/self/total-order-price")
	ResponseEntity<BigDecimal> getMyTotalOrderPrice();

	@PostMapping("/api/users/send-email/dormant-to-active")
	ResponseEntity<Void> sendEmailDormantToActive(@RequestParam String email);

	@GetMapping("/api/users/check-email/dormant-to-active")
	ResponseEntity<Void> checkEmailDormantToActive(@RequestParam String email, @RequestParam String certifyCode);

	@GetMapping("/api/addresses/default")
	ResponseEntity<Optional<GetAddressResponse>> getDefaultAddress();

	@GetMapping("/api/users")
	ResponseEntity<Page<GetUserInfoResponse>> getUsers(Pageable pageable);

	// TODO: 회원 정보 수정
}
