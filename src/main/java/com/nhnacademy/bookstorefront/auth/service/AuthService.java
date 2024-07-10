package com.nhnacademy.bookstorefront.auth.service;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.dto.response.LoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.SignUpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
	ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

	void logout();

	ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest);

	ResponseEntity<Boolean> isEmailExist(String email);

	void setTokensInSession(String accessToken, String refreshToken, HttpSession session);

	boolean hasTokensInCookie(HttpServletRequest request);
}
