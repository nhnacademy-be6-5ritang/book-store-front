package com.nhnacademy.bookstorefront.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.dto.response.LoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.PaycoLoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.SignUpResponse;
import com.nhnacademy.bookstorefront.auth.feignclient.AuthClient;
import com.nhnacademy.bookstorefront.auth.service.impl.AuthServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SpringJUnitConfig
class AuthServiceImplTest {

	private AuthService authService;

	@MockBean
	private AuthClient authClient;

	@MockBean
	private HttpServletResponse response;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		authService = new AuthServiceImpl(authClient);
	}

	@Test
	void testSignUp() {
		SignUpRequest signUpRequest = new SignUpRequest(
			"John Doe",
			"john.doe@example.com",
			"password123",
			1990,
			1,
			1,
			"01012345678"
		);

		SignUpResponse signUpResponse = new SignUpResponse(
			1L,
			"John Doe",
			"john.doe@example.com",
			LocalDate.of(1990, 1, 1),
			"01012345678",
			"ACTIVE",
			"USER"
		);

		when(authClient.requestSignUp(any(SignUpRequest.class)))
			.thenReturn(ResponseEntity.ok(signUpResponse));

		ResponseEntity<SignUpResponse> response = authService.signUp(signUpRequest);
		assertEquals(signUpResponse, response.getBody());
	}

	@Test
	void testSendEmailSignUp() {
		String email = "john.doe@example.com";

		when(authClient.sendEmailSignUp(email)).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> response = authService.sendEmailSignUp(email);
		assertEquals(ResponseEntity.ok().build(), response);
	}

	@Test
	void testCheckEmailSignUp() {
		String email = "john.doe@example.com";
		String certifyCode = "123456";

		when(authClient.checkEmailSignUp(email, certifyCode)).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> response = authService.checkEmailSignUp(email, certifyCode);
		assertEquals(ResponseEntity.ok().build(), response);
	}

	@Test
	void testLogin() {
		LoginRequest loginRequest = new LoginRequest("john.doe@example.com", "password123");

		LoginResponse loginResponse = new LoginResponse(
			"accessToken",
			"refreshToken",
			LocalDateTime.now()
		);

		when(authClient.requestLogin(any(LoginRequest.class)))
			.thenReturn(ResponseEntity.ok(loginResponse));

		ResponseEntity<LoginResponse> response = authService.login(loginRequest);
		assertEquals(loginResponse, response.getBody());
	}

	@Test
	void testLogout() {
		authService.logout();
		verify(authClient, times(1)).requestLogout();
	}

	@Test
	void testHasTokensInCookie() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Cookie[] cookies = new Cookie[] {
			new Cookie("Authorization", "accessToken"),
			new Cookie("Refresh-Token", "refreshToken")
		};

		when(request.getCookies()).thenReturn(cookies);

		boolean hasTokens = authService.hasTokensInCookie(request);
		assertTrue(hasTokens);
	}

	@Test
	void testUpdateLastLoginAt() {
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		LocalDateTime lastLoginAt = LocalDateTime.now();

		doNothing().when(authClient).updateLastLoginAt(anyString(), anyString(), any(LocalDateTime.class));

		authService.updateLastLoginAt(accessToken, refreshToken, lastLoginAt);
		verify(authClient, times(1)).updateLastLoginAt(accessToken, refreshToken, lastLoginAt);
	}

	@Test
	void testGetTokensForPaycoUser() {
		String memberNumber = "12345";
		PaycoLoginResponse paycoLoginResponse = new PaycoLoginResponse("accessToken", "refreshToken");

		when(authClient.getTokensForPaycoUser(memberNumber))
			.thenReturn(ResponseEntity.ok(paycoLoginResponse));

		authService.getTokensForPaycoUser(memberNumber, response);

		verify(response, times(1)).addCookie(argThat(cookie ->
			"Authorization".equals(cookie.getName()) &&
				"accessToken".equals(cookie.getValue())
		));

		verify(response, times(1)).addCookie(argThat(cookie ->
			"Refresh-Token".equals(cookie.getName()) &&
				"refreshToken".equals(cookie.getValue())
		));
	}
}
