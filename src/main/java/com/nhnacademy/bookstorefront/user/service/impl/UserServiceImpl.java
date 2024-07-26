package com.nhnacademy.bookstorefront.user.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserInfoResponse;
import com.nhnacademy.bookstorefront.user.feignclient.UserClient;
import com.nhnacademy.bookstorefront.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserClient userClient;

	@Override
	public ResponseEntity<Void> getAdminPage() {
		return userClient.getAdminPage();
	}

	@Override
	public ResponseEntity<Page<GetUserInfoResponse>> getUsers(Pageable pageable) {
		return userClient.getUsers(pageable);
	}

	@Override
	public ResponseEntity<GetMyUserInfoResponse> getMyUserInfo() {
		return userClient.getMyUserInfo();
	}

	@Override
	public ResponseEntity<Optional<GetAddressResponse>> getDefaultAddress() {
		return userClient.getDefaultAddress();
	}

	@Override
	public ResponseEntity<Void> withdrawUser(HttpServletResponse response) {
		return userClient.withdrawUser();
	}

	@Override
	public ResponseEntity<BigDecimal> getMyTotalOrderPrice() {
		return userClient.getMyTotalOrderPrice();
	}

	@Override
	public ResponseEntity<Void> sendEmailDormantToActive(String email) {
		return userClient.sendEmailDormantToActive(email);
	}

	@Override
	public ResponseEntity<Void> checkEmailDormantToActive(String email, String certifyCode) {
		return userClient.checkEmailDormantToActive(email, certifyCode);
	}
}
