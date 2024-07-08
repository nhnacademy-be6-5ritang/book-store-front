package com.nhnacademy.bookstorefront.user.service;

import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
	// ResponseEntity<Void> login(LoginRequest loginRequest);
	//
	// void logout(String refreshToken);
	//
	// void signUp(SignUpRequest signUpRequest);
	//
	// ResponseEntity<Boolean> isEmailExist(String email);
	ResponseEntity<Void> dormantUser(HttpServletResponse response);
}
