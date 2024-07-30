package com.nhnacademy.bookstorefront.user.service;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.address.dto.response.GetAddressResponse;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserInfoRequest;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserRoleRequest;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserInfoResponse;
import com.nhnacademy.bookstorefront.user.dto.response.UpdateUserInfoResponse;
import com.nhnacademy.bookstorefront.user.feignclient.UserClient;
import com.nhnacademy.bookstorefront.user.service.impl.UserServiceImpl;

class UserServiceImplTest {

	@Mock
	private UserClient userClient;

	@InjectMocks
	private UserServiceImpl userService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testGetAdminPage() {
		when(userClient.getAdminPage()).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> response = userService.getAdminPage();
		verify(userClient, times(1)).getAdminPage();
		assert (response.getStatusCode().is2xxSuccessful());
	}

	@Test
	void testGetUsers() {
		Pageable pageable = Pageable.unpaged();
		GetUserInfoResponse userInfo = new GetUserInfoResponse(
			1L, "John Doe", "john.doe@example.com", LocalDateTime.now(),
			Collections.singletonList("USER"), "Gold", "Active"
		);
		Page<GetUserInfoResponse> userInfoPage = new PageImpl<>(Collections.singletonList(userInfo), pageable, 1);

		when(userClient.getUsers(pageable)).thenReturn(ResponseEntity.ok(userInfoPage));

		ResponseEntity<Page<GetUserInfoResponse>> response = userService.getUsers(pageable);
		verify(userClient, times(1)).getUsers(pageable);
		assert (response.getBody().getContent().size() == 1);
	}

	@Test
	void testGetMyUserInfo() {
		GetMyUserInfoResponse myUserInfo = new GetMyUserInfoResponse(
			"John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1),
			"12345678901", LocalDateTime.now(), Collections.singletonList("USER"),
			"Gold", "Active", BigDecimal.valueOf(100.50)
		);
		when(userClient.getMyUserInfo()).thenReturn(ResponseEntity.ok(myUserInfo));

		ResponseEntity<GetMyUserInfoResponse> response = userService.getMyUserInfo();
		verify(userClient, times(1)).getMyUserInfo();
		assert (response.getBody().name().equals("John Doe"));
		assert (response.getBody().points().compareTo(BigDecimal.valueOf(100.50)) == 0);
	}

	@Test
	void testGetDefaultAddress() {
		GetAddressResponse address = new GetAddressResponse(
			1L, "12345", "Base Address", "Detail Address", "Home", true
		);
		when(userClient.getDefaultAddress()).thenReturn(ResponseEntity.ok(Optional.of(address)));

		ResponseEntity<Optional<GetAddressResponse>> response = userService.getDefaultAddress();
		verify(userClient, times(1)).getDefaultAddress();
		assert (response.getBody().isPresent());
		assert (response.getBody().get().isDefault());
	}

	@Test
	void testWithdrawUser() {
		when(userClient.withdrawUser()).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> response = userService.withdrawUser(null);
		verify(userClient, times(1)).withdrawUser();
		assert (response.getStatusCode().is2xxSuccessful());
	}

	@Test
	void testGetMyTotalOrderPrice() {
		BigDecimal totalOrderPrice = BigDecimal.valueOf(123.45);
		when(userClient.getMyTotalOrderPrice()).thenReturn(ResponseEntity.ok(totalOrderPrice));

		ResponseEntity<BigDecimal> response = userService.getMyTotalOrderPrice();
		verify(userClient, times(1)).getMyTotalOrderPrice();
		assert (response.getBody().compareTo(totalOrderPrice) == 0);
	}

	@Test
	void testSendEmailDormantToActive() {
		String email = "john.doe@example.com";
		when(userClient.sendEmailDormantToActive(email)).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> response = userService.sendEmailDormantToActive(email);
		verify(userClient, times(1)).sendEmailDormantToActive(email);
		assert (response.getStatusCode().is2xxSuccessful());
	}

	@Test
	void testCheckEmailDormantToActive() {
		String email = "john.doe@example.com";
		String certifyCode = "123456";
		when(userClient.checkEmailDormantToActive(email, certifyCode)).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> response = userService.checkEmailDormantToActive(email, certifyCode);
		verify(userClient, times(1)).checkEmailDormantToActive(email, certifyCode);
		assert (response.getStatusCode().is2xxSuccessful());
	}

	@Test
	void testUpdateUserInfo() {
		UpdateUserInfoRequest updateRequest = new UpdateUserInfoRequest(
			"John Doe", "password123", LocalDate.of(1990, 1, 1), "12345678901"
		);
		UpdateUserInfoResponse response = new UpdateUserInfoResponse(
			"John Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "12345678901"
		);
		when(userClient.updateUser(updateRequest)).thenReturn(ResponseEntity.ok(response));

		UpdateUserInfoResponse actualResponse = userService.updateUserInfo(updateRequest);
		verify(userClient, times(1)).updateUser(updateRequest);
		assert (actualResponse.name().equals("John Doe"));
	}

	@Test
	void testUpdateUserRole() {
		UpdateUserRoleRequest updateRoleRequest = UpdateUserRoleRequest.builder()
			.userId(1L)
			.roleName(Collections.singletonList("ADMIN"))
			.build();

		when(userClient.updateRole(updateRoleRequest)).thenReturn(ResponseEntity.ok().build());

		userService.updateUserRole(updateRoleRequest);
		verify(userClient, times(1)).updateRole(updateRoleRequest);
	}

	@Test
	void testPaycoConnect() {
		String memberId = "member123";
		doNothing().when(userClient).paycoConnect(memberId);

		userService.paycoConnect(memberId);
		verify(userClient, times(1)).paycoConnect(memberId);
	}
}