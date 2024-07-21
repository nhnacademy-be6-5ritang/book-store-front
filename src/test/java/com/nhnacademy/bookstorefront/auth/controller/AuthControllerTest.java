package com.nhnacademy.bookstorefront.auth.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.dto.response.LoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.SignUpResponse;
import com.nhnacademy.bookstorefront.auth.service.AuthService;
import com.nhnacademy.bookstorefront.global.config.CacheConfig;
import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@MockBean
	private UserAndCouponService userAndCouponService;

	@MockBean
	private CacheConfig cacheConfig;

	@MockBean
	private GlobalDataControllerAdvice globalDataControllerAdvice;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService, userAndCouponService))
			.build();
	}

	@Test
	void testSignUpPage() throws Exception {
		mockMvc.perform(get("/auth/sign-up"))
			.andExpect(status().isOk())
			.andExpect(view().name("auth/sign-up"));
	}

	@Test
	void testSignUpProcessSuccess() throws Exception {
		SignUpRequest signUpRequest = new SignUpRequest(
			"John Doe", "john.doe@example.com", "password123",
			1990, 1, 1, "01012345678"
		);

		SignUpResponse signUpResponse = new SignUpResponse(
			1L, "John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1),
			"01012345678", "ACTIVE", "GOLD"
		);

		when(authService.signUp(any(SignUpRequest.class)))
			.thenReturn(ResponseEntity.ok(signUpResponse));

		mockMvc.perform(post("/auth/sign-up")
				.contentType("application/x-www-form-urlencoded")
				.param("name", signUpRequest.name())
				.param("email", signUpRequest.email())
				.param("password", signUpRequest.password())
				.param("year", signUpRequest.year().toString())
				.param("month", signUpRequest.month().toString())
				.param("day", signUpRequest.day().toString())
				.param("contact", signUpRequest.contact()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/auth/login"));

		verify(userAndCouponService).createWelcomeCoupon(1L);
	}

	@Test
	void testSignUpProcessFailure() throws Exception {
		SignUpRequest signUpRequest = new SignUpRequest(
			"John Doe", "john.doe@example.com", "password123",
			1990, 1, 1, "01012345678"
		);

		// 여기서 RuntimeException을 발생시키며, 이 예외 메시지를 `error` 파라미터에 포함
		when(authService.signUp(any(SignUpRequest.class)))
			.thenThrow(new RuntimeException("409 Conflict"));

		// 예상 URL 인코딩 문자열
		String expectedErrorMessage = URLEncoder.encode("해당 이메일은 이미 존재하는 이메일입니다.", StandardCharsets.UTF_8);
		String expectedRedirectUrl = "/auth/sign-up?error=" + expectedErrorMessage;

		mockMvc.perform(post("/auth/sign-up")
				.contentType("application/x-www-form-urlencoded")
				.param("name", signUpRequest.name())
				.param("email", signUpRequest.email())
				.param("password", signUpRequest.password())
				.param("year", signUpRequest.year().toString())
				.param("month", signUpRequest.month().toString())
				.param("day", signUpRequest.day().toString())
				.param("contact", signUpRequest.contact()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl(expectedRedirectUrl));
	}

	@Test
	void testLoginPage() throws Exception {
		mockMvc.perform(get("/auth/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("auth/login"));
	}

	@Test
	void testLoginProcessSuccess() throws Exception {
		LoginRequest loginRequest = new LoginRequest("john.doe@example.com", "password123");
		LoginResponse loginResponse = new LoginResponse("access-token", "refresh-token", LocalDateTime.now());

		when(authService.login(any(LoginRequest.class)))
			.thenReturn(ResponseEntity.ok(loginResponse));

		mockMvc.perform(post("/auth/login")
				.contentType("application/x-www-form-urlencoded")
				.param("email", loginRequest.email())
				.param("password", loginRequest.password()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));

		verify(authService).updateLastLoginAt(anyString(), anyString(), any(LocalDateTime.class));
	}

	@Test
	void testLogout() throws Exception {
		mockMvc.perform(post("/auth/logout"))
			.andExpect(status().isOk());

		verify(authService).logout();
	}
}
