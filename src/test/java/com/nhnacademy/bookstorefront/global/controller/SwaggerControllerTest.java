package com.nhnacademy.bookstorefront.global.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.nhnacademy.bookstorefront.global.feignclient.SwaggerApiClient;

class SwaggerControllerTest {

	@Mock
	private SwaggerApiClient swaggerApiClient;

	@Mock
	private Model model;

	@InjectMocks
	private SwaggerController swaggerController;

	public SwaggerControllerTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetCouponApi_Success() {
		String mockSwaggerJson = "{\"swagger\": \"2.0\"}";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(mockSwaggerJson);
		when(swaggerApiClient.getSwaggerJson()).thenReturn(responseEntity);

		String viewName = swaggerController.getCouponApi(model);

		verify(swaggerApiClient, times(1)).getSwaggerJson();
		verify(model, times(1)).addAttribute("swaggerJson", mockSwaggerJson);
		assertEquals("api/coupon-api", viewName);
	}

	@Test
	void testGetCouponApi_Exception() {
		when(swaggerApiClient.getSwaggerJson()).thenThrow(new RuntimeException("Service unavailable"));

		String viewName = swaggerController.getBackApi(model);

		verify(swaggerApiClient, times(1)).getBackSwaggerJson();
		verify(model, times(1)).addAttribute("swaggerJson", "{}");
		assertEquals("api/back-api", viewName);
	}

	@Test
	void testGetBackApi_Success() {
		String mockSwaggerJson = "{}";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(mockSwaggerJson);
		when(swaggerApiClient.getSwaggerJson()).thenReturn(responseEntity);

		String viewName = swaggerController.getBackApi(model);

		verify(swaggerApiClient, times(1)).getBackSwaggerJson();
		verify(model, times(1)).addAttribute("swaggerJson", mockSwaggerJson);
		assertEquals("api/back-api", viewName);
	}

	@Test
	void testGetBackApi_Exception() {
		when(swaggerApiClient.getSwaggerJson()).thenThrow(new RuntimeException("Service unavailable"));

		String viewName = swaggerController.getCouponApi(model);

		verify(swaggerApiClient, times(1)).getBackSwaggerJson();
		verify(model, times(1)).addAttribute("swaggerJson", "{}");
		assertEquals("api/coupon-api", viewName);
	}

	@Test
	void testGetAuthApi_Success() {
		String mockSwaggerJson = "{}";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(mockSwaggerJson);
		when(swaggerApiClient.getSwaggerJson()).thenReturn(responseEntity);

		String viewName = swaggerController.getAuthApi(model);

		verify(swaggerApiClient, times(1)).getAuthSwaggerJson();
		verify(model, times(1)).addAttribute("swaggerJson", mockSwaggerJson);
		assertEquals("api/auth-api", viewName);
	}

	@Test
	void testGetAuthApi_Exception() {
		when(swaggerApiClient.getSwaggerJson()).thenThrow(new RuntimeException("Service unavailable"));

		String viewName = swaggerController.getAuthApi(model);

		verify(swaggerApiClient, times(1)).getAuthSwaggerJson();
		verify(model, times(1)).addAttribute("swaggerJson", "{}");
		assertEquals("api/auth-api", viewName);
	}

}
