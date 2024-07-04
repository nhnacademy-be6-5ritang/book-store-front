package com.nhnacademy.bookstorefront.auth.service;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;

public interface AuthService {
	ResponseEntity<Void> login(LoginRequest loginRequest);

	void logout(String refreshToken);
}
