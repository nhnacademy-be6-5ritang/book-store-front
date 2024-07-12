package com.nhnacademy.bookstorefront.auth.feignclient;

import java.time.LocalDateTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.dto.response.LoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.SignUpResponse;

@FeignClient(name = "auth-feign-client", url = "http://localhost:8090")
public interface AuthClient {

	@PostMapping("/auth/login")
	ResponseEntity<LoginResponse> requestLogin(@RequestBody LoginRequest loginRequest);

	@PostMapping("/auth/logout")
	ResponseEntity<Void> requestLogout();

	@PostMapping("/api/users")
	ResponseEntity<SignUpResponse> requestSignUp(@RequestBody SignUpRequest signUpRequest);

	@PostMapping("/api/users/send-email/sign-up")
	ResponseEntity<Void> sendEmailSignUp(@RequestParam("email") String email);

	@GetMapping("/api/users/check-email/sign-up")
	ResponseEntity<Void> checkEmailSignUp(
		@RequestParam("email") String email, @RequestParam("certifyCode") String certifyCode
	);

	@PatchMapping("/api/users/last-login-at")
	void updateLastLoginAt(
		@RequestHeader("Authorization") String accessToken,
		@RequestHeader("Refresh-Token") String refreshToken,
		@RequestBody LocalDateTime lastLoginAt
	);

}
