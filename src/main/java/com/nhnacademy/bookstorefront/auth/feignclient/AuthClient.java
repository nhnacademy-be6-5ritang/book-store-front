package com.nhnacademy.bookstorefront.auth.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;

@FeignClient(name = "auth-feign-client", url = "http://localhost:8090")
public interface AuthClient {

	@PostMapping("/auth/login")
	ResponseEntity<Void> requestLogin(@RequestBody LoginRequest loginRequest);

	@PostMapping("/auth/logout")
	void requestLogout(@CookieValue("Refresh-Token") String refreshToken);
}
