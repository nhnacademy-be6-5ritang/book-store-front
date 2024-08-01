package com.nhnacademy.bookstorefront.auth.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.nhnacademy.bookstorefront.auth.service.AuthService;
import com.nhnacademy.bookstorefront.cache.service.CacheService;
import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;
import com.nhnacademy.bookstorefront.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@WebMvcTest(PaycoController.class)
class PaycoControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private CacheService cacheService;

	@MockBean
	private GlobalDataControllerAdvice globalDataControllerAdvice;

	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private AuthService authService;

	@MockBean
	private UserService userService;

	@Value("${payco.client-id}")
	private String clientId;

	@Value("${payco.client-secret}")
	private String clientSecret;

	@Value("${payco.redirect-uri}")
	private String redirectUri;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new PaycoController(restTemplate, authService, userService))
			.build();
	}

	@Test
	void testPaycoConnect() throws Exception {
		String code = "testCode";
		String tokenUrl = "https://id.payco.com/oauth2.0/token";
		String accessToken = "testAccessToken";
		String userInfoUrl = "https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json";
		String userInfoResponseBody = "{\"data\":{\"member\":{\"idNo\":\"12345\"}}}";

		// Mock the token response
		when(restTemplate.postForEntity(eq(tokenUrl), any(HttpEntity.class), eq(String.class)))
			.thenReturn(ResponseEntity.ok("{\"access_token\":\"" + accessToken + "\"}"));

		// Mock the user info response
		when(restTemplate.postForEntity(eq(userInfoUrl), any(HttpEntity.class), eq(String.class)))
			.thenReturn(ResponseEntity.ok(userInfoResponseBody));

		// Mock the userService method
		doNothing().when(userService).paycoConnect("12345");

		mockMvc.perform(get("/auth/payco/connect")
				.param("code", code))
			.andExpect(status().is2xxSuccessful());

		verify(restTemplate, times(1)).postForEntity(eq(tokenUrl), any(HttpEntity.class), eq(String.class));
		verify(restTemplate, times(1)).postForEntity(eq(userInfoUrl), any(HttpEntity.class), eq(String.class));
	}

	@Test
	void testPaycoCallback() throws Exception {
		String code = "testCode";
		String state = "testState";
		String tokenUrl = "https://id.payco.com/oauth2.0/token";
		String accessToken = "testAccessToken";
		String userInfoUrl = "https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json";
		String userInfoResponseBody = "{\"data\":{\"member\":{\"idNo\":\"12345\"}}}";

		// Mock the token response
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String tokenResponseBody = "{\"access_token\":\"" + accessToken + "\"}";

		when(restTemplate.postForEntity(eq(tokenUrl), any(HttpEntity.class), eq(String.class)))
			.thenReturn(ResponseEntity.ok(tokenResponseBody));

		// Mock the user info response
		when(restTemplate.postForEntity(eq(userInfoUrl), any(HttpEntity.class), eq(String.class)))
			.thenReturn(ResponseEntity.ok(userInfoResponseBody));

		// Mock the authService method
		doNothing().when(authService).getTokensForPaycoUser(eq("12345"), any(HttpServletResponse.class));

		mockMvc.perform(get("/auth/payco/callback")
				.param("code", code)
				.param("state", state))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));

		verify(restTemplate, times(1)).postForEntity(eq(tokenUrl), any(HttpEntity.class), eq(String.class));
		verify(restTemplate, times(1)).postForEntity(eq(userInfoUrl), any(HttpEntity.class), eq(String.class));
		verify(authService, times(1)).getTokensForPaycoUser(eq("12345"), any(HttpServletResponse.class));
	}

	@Test
	void testPaycoCallbackWithError() throws Exception {
		String code = "testCode";
		String state = "testState";
		String tokenUrl = "https://id.payco.com/oauth2.0/token";

		// Mock the token response with error
		when(restTemplate.postForEntity(eq(tokenUrl), any(HttpEntity.class), eq(String.class)))
			.thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"));

		mockMvc.perform(get("/auth/payco/callback")
				.param("code", code)
				.param("state", state))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/auth/login?error=" + URLEncoder.encode("로그인 실패", StandardCharsets.UTF_8)));
	}
}