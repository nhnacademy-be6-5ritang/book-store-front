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
		// Given
		String mockSwaggerJson = "{\"swagger\": \"2.0\"}";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(mockSwaggerJson);
		when(swaggerApiClient.getSwaggerJson()).thenReturn(responseEntity);

		// When
		String viewName = swaggerController.getCouponApi(model);

		// Then
		verify(swaggerApiClient, times(1)).getSwaggerJson();
		verify(model, times(1)).addAttribute("swaggerJson", mockSwaggerJson);
		assertEquals("api/coupon-api", viewName);
	}

	@Test
	void testGetCouponApi_Exception() {
		// Given
		when(swaggerApiClient.getSwaggerJson()).thenThrow(new RuntimeException("Service unavailable"));

		// When
		String viewName = swaggerController.getCouponApi(model);

		// Then
		verify(swaggerApiClient, times(1)).getSwaggerJson();
		verify(model, times(1)).addAttribute("swaggerJson", "{}");
		assertEquals("api/coupon-api", viewName);
	}
}