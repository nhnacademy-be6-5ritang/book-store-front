package com.nhnacademy.bookstorefront.auth.service.impl;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.dto.response.LoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.SignUpResponse;
import com.nhnacademy.bookstorefront.auth.feignclient.AuthClient;
import com.nhnacademy.bookstorefront.auth.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final AuthClient authClient;

	@Override
	public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) {
		return authClient.requestSignUp(signUpRequest);
	}

	@Override
	public ResponseEntity<Boolean> isEmailExist(String email) {
		return authClient.isEmailExist(email);
	}

	@Override
	public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
		return authClient.requestLogin(loginRequest);
	}

	@Override
	public void logout() {
		authClient.requestLogout();
	}

	@Override
	public void setTokensInSession(String accessToken, String refreshToken, HttpSession session) {
		session.setAttribute("accessToken", accessToken);
		session.setAttribute("refreshToken", refreshToken);
	}

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

	@Override
	public void updateLastLoginAt(String accessToken, String refreshToken, LocalDateTime lastLoginAt) {
		authClient.updateLastLoginAt(accessToken, refreshToken, lastLoginAt);
	}
}
