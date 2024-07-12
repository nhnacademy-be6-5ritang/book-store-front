package com.nhnacademy.bookstorefront.user.service.impl;

import java.math.BigDecimal;

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
	public ResponseEntity<Void> withdrawUser(HttpServletResponse response) {
		return userClient.withdrawUser();
	}

	@Override
	public ResponseEntity<BigDecimal> getMyTotalOrderPrice() {
		return userClient.getMyTotalOrderPrice();
	}
}
