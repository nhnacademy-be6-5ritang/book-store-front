package com.nhnacademy.bookstorefront.auth.service;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;

public interface AuthService {
	ResponseEntity<Void> login(LoginRequest loginRequest);

	void logout();

	void signUp(SignUpRequest signUpRequest);

	ResponseEntity<Boolean> isEmailExist(String email);
}
