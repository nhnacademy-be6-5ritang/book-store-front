package com.nhnacademy.bookstorefront.user.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.feignclient.UserClient;
import com.nhnacademy.bookstorefront.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserClient userClient;

	@Override
	public ResponseEntity<GetMyUserInfoResponse> getMyUserInfo() {
		return userClient.getMyUserInfo();
	}

	@Override
	public ResponseEntity<Void> dormantUser(HttpServletResponse response) {
		return userClient.dormantUser();
	}
}
