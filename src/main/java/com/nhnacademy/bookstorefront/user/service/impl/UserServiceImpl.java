package com.nhnacademy.bookstorefront.user.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.user.feignclient.UserClient;
import com.nhnacademy.bookstorefront.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserClient userClient;

	// @Override
	// public void signUp(SignUpRequest signUpRequest) {
	// 	authClient.requestSignUp(signUpRequest);
	// }
	//
	// @Override
	// public ResponseEntity<Boolean> isEmailExist(String email) {
	// 	return authClient.isEmailExist(email);
	// }
	//
	// @Override
	// public ResponseEntity<Void> login(LoginRequest loginRequest) {
	// 	return authClient.requestLogin(loginRequest);
	// }
	//
	// @Override
	// public void logout(String refreshToken) {
	// 	authClient.requestLogout(refreshToken);
	// }
	@Override
	public ResponseEntity<Void> dormantUser() {
		return userClient.dormantUser();
	}
}
