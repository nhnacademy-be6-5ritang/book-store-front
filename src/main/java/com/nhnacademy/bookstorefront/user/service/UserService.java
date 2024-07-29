package com.nhnacademy.bookstorefront.user.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserInfoResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
	ResponseEntity<GetMyUserInfoResponse> getMyUserInfo();

	ResponseEntity<Void> withdrawUser(HttpServletResponse response);

	ResponseEntity<BigDecimal> getMyTotalOrderPrice();

	ResponseEntity<Void> sendEmailDormantToActive(String email);

	ResponseEntity<Void> checkEmailDormantToActive(String email, String certifyCode);

	ResponseEntity<Optional<GetAddressResponse>> getDefaultAddress();

	ResponseEntity<Page<GetUserInfoResponse>> getUsers(Pageable pageable);

	ResponseEntity<Void> getAdminPage();
}
