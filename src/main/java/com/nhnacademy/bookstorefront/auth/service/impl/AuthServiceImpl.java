package com.nhnacademy.bookstorefront.auth.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.dto.response.LoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.PaycoLoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.SignUpResponse;
import com.nhnacademy.bookstorefront.auth.feignclient.AuthClient;
import com.nhnacademy.bookstorefront.auth.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final AuthClient authClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) {
		return authClient.requestSignUp(signUpRequest);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> sendEmailSignUp(String email) {
		return authClient.sendEmailSignUp(email);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> checkEmailSignUp(String email, String certifyCode) {
		return authClient.checkEmailSignUp(email, certifyCode);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
		return authClient.requestLogin(loginRequest);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void logout() {
		authClient.requestLogout();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public boolean hasTokensInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return false;
		}

		boolean hasAccessToken = false;
		boolean hasRefreshToken = false;

		for (Cookie cookie : cookies) {
			if ("Authorization".equals(cookie.getName())) {
				hasAccessToken = true;
			}
			if ("Refresh-Token".equals(cookie.getName())) {
				hasRefreshToken = true;
			}
		}

		return hasAccessToken && hasRefreshToken;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void updateLastLoginAt(String accessToken, String refreshToken, LocalDateTime lastLoginAt) {
		authClient.updateLastLoginAt(accessToken, refreshToken, lastLoginAt);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void getTokensForPaycoUser(String memberNumber, HttpServletResponse response) {
		PaycoLoginResponse paycoLoginResponse = authClient.getTokensForPaycoUser(memberNumber).getBody();
		String accessToken = paycoLoginResponse.accessToken();
		String refreshToken = paycoLoginResponse.refreshToken();

		if (accessToken != null) {
			response.addCookie(createCookie("Authorization", accessToken));
		}

		if (refreshToken != null) {
			response.addCookie(createCookie("Refresh-Token", refreshToken));
		}

		updateLastLoginAt(accessToken, refreshToken, LocalDateTime.now());
	}

	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, URLEncoder.encode(value, StandardCharsets.UTF_8));
		// cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		return cookie;
	}
}
