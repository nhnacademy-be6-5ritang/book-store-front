package com.nhnacademy.bookstorefront.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.cache.service.CacheService;
import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserInfoRequest;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.UpdateUserInfoResponse;
import com.nhnacademy.bookstorefront.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@WebMvcTest(UserController.class)
class UserControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private CacheService cacheService;

	@MockBean
	GlobalDataControllerAdvice globalDataControllerAdvice;

	@MockBean
	private UserService userService;
	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService))
			.build();
	}

	@Test
	void testMyUserInfoPage() throws Exception {
		// Arrange
		GetMyUserInfoResponse userInfo = new GetMyUserInfoResponse(
			"John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "12345678901",
			LocalDateTime.now(), Collections.singletonList("USER"), "Silver", "Active", BigDecimal.valueOf(100.50)
		);
		ResponseEntity<GetMyUserInfoResponse> userResponse = ResponseEntity.ok(userInfo);
		ResponseEntity<BigDecimal> totalOrderPriceResponse = ResponseEntity.ok(BigDecimal.valueOf(250.75));
		ResponseEntity<Optional<GetAddressResponse>> addressResponse = ResponseEntity.ok(
			Optional.of(new GetAddressResponse(
				1L, "12345", "Base Address", "Detail Address", "Alias", true
			)));

		when(userService.getMyUserInfo()).thenReturn(userResponse);
		when(userService.getMyTotalOrderPrice()).thenReturn(totalOrderPriceResponse);
		when(userService.getDefaultAddress()).thenReturn(addressResponse);

		// Act & Assert
		mockMvc.perform(get("/users/my-page"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/my-page"))
			.andExpect(model().attributeExists("myUserInfo"))
			.andExpect(model().attributeExists("roles"))
			.andExpect(model().attributeExists("myTotalOrderPrice"))
			.andExpect(model().attributeExists("myAddress"));
	}

	@Test
	void testSendEmailDormantToActive() throws Exception {
		// Arrange
		String email = "john.doe@example.com";
		when(userService.sendEmailDormantToActive(email)).thenReturn(ResponseEntity.ok().build());

		// Act & Assert
		mockMvc.perform(post("/users/send-email/dormant-to-active")
				.param("email", email))
			.andExpect(status().isOk());
	}

	@Test
	void testCheckEmailDormantToActive() throws Exception {
		// Arrange
		String email = "john.doe@example.com";
		String certifyCode = "123456";
		when(userService.checkEmailDormantToActive(email, certifyCode)).thenReturn(ResponseEntity.ok().build());

		// Act & Assert
		mockMvc.perform(get("/users/check-email/dormant-to-active")
				.param("email", email)
				.param("certifyCode", certifyCode))
			.andExpect(status().isOk());
	}

	@Test
	void testDormantCertifyPage() throws Exception {
		// Act & Assert
		mockMvc.perform(get("/users/dormant-certify"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/dormant-certify"));
	}

	@Test
	void testWithdrawUser() throws Exception {
		// Arrange
		when(userService.withdrawUser(any(HttpServletResponse.class))).thenReturn(ResponseEntity.ok().build());

		// Act & Assert
		mockMvc.perform(patch("/users/withdraw"))
			.andExpect(status().isOk());
	}

	@Test
	void testUpdateInfo() throws Exception {
		// Arrange
		GetMyUserInfoResponse userInfo = new GetMyUserInfoResponse(
			"John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "12345678901",
			LocalDateTime.now(), Collections.singletonList("USER"), "Silver", "Active", BigDecimal.valueOf(100.50)
		);
		ResponseEntity<GetMyUserInfoResponse> userResponse = ResponseEntity.ok(userInfo);
		when(userService.getMyUserInfo()).thenReturn(userResponse);

		// Act & Assert
		mockMvc.perform(get("/users/update-info"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/update-info"))
			.andExpect(model().attributeExists("myUserInfo"));
	}

	@Test
	void testUpdateInfoPost() throws Exception {
		UpdateUserInfoRequest updateRequest = new UpdateUserInfoRequest(
			"John Doe", "password123", LocalDate.of(1990, 1, 1), "12345678901"
		);

		UpdateUserInfoResponse response = new UpdateUserInfoResponse(
			"John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "12345678901"
		);

		when(userService.updateUserInfo(updateRequest)).thenReturn(response);

		mockMvc.perform(MockMvcRequestBuilders.post("/users/update-info")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jacksonObjectMapper.writeValueAsString(updateRequest)))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/users/my-page"));

	}
}