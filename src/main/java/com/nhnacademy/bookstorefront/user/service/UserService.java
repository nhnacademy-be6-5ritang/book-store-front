package com.nhnacademy.bookstorefront.user.service;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
	ResponseEntity<GetMyUserInfoResponse> getMyUserInfo();

	ResponseEntity<Void> withdrawUser(HttpServletResponse response);

	ResponseEntity<BigDecimal> getMyTotalOrderPrice();
}
