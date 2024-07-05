package com.nhnacademy.bookstorefront.auth.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.feignclient.AuthClient;
import com.nhnacademy.bookstorefront.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final AuthClient authClient;

	@Override
	public void signUp(SignUpRequest signUpRequest) {
		authClient.requestSignUp(signUpRequest);
	}

	@Override
	public ResponseEntity<Boolean> isEmailExist(String email) {
		return authClient.isEmailExist(email);
	}

	@Override
	public ResponseEntity<Void> login(LoginRequest loginRequest) {
		return authClient.requestLogin(loginRequest);
	}

	@Override
	public void logout() {
		authClient.requestLogout();
	}
}
