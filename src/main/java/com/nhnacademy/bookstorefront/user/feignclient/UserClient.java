package com.nhnacademy.bookstorefront.user.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-feign-client", url = "http://localhost:8090")
public interface UserClient {

	@GetMapping("/api/users/self")
	void getMyUserInfo();

	// @PostMapping("/auth/login")
	// ResponseEntity<Void> requestLogin(@RequestBody LoginRequest loginRequest);
	//
	// @PostMapping("/auth/logout")
	// void requestLogout(@CookieValue("Refresh-Token") String refreshToken);
}
