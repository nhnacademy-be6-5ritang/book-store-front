package com.nhnacademy.bookstorefront.user.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserInfoRequest;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserRoleRequest;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserGradeResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.UpdateUserInfoResponse;
import com.nhnacademy.bookstorefront.user.feignclient.UserClient;
import com.nhnacademy.bookstorefront.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserClient userClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> getAdminPage() {
		return userClient.getAdminPage();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Page<GetUserInfoResponse>> getUsers(Pageable pageable) {
		return userClient.getUsers(pageable);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<GetMyUserInfoResponse> getMyUserInfo() {
		return userClient.getMyUserInfo();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Optional<GetAddressResponse>> getDefaultAddress() {
		return userClient.getDefaultAddress();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> withdrawUser(HttpServletResponse response) {
		return userClient.withdrawUser();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<BigDecimal> getMyTotalOrderPrice() {
		return userClient.getMyTotalOrderPrice();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> sendEmailDormantToActive(String email) {
		return userClient.sendEmailDormantToActive(email);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> checkEmailDormantToActive(String email, String certifyCode) {
		return userClient.checkEmailDormantToActive(email, certifyCode);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public UpdateUserInfoResponse updateUserInfo(UpdateUserInfoRequest updateUserInfoRequest) {
		return userClient.updateUser(updateUserInfoRequest).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void updateUserRole(UpdateUserRoleRequest updateUserRoleRequest) {
		userClient.updateRole(updateUserRoleRequest);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void paycoConnect(String paycoId) {
		userClient.paycoConnect(paycoId);
	}

	@Override
	public List<GetUserGradeResponse> getUserGrades() {
		return userClient.getUserGrades().getBody();
	}
}
