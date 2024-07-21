package com.nhnacademy.bookstorefront.deliverystatus.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GetDeliveryStatusResponseTest {

	@Test
	void testGetDeliveryStatusResponse() {
		Long id = 1L;
		String name = "Pending";

		GetDeliveryStatusResponse response = new GetDeliveryStatusResponse(id, name);

		assertEquals(id, response.deliveryStatusId());
		assertEquals(name, response.deliveryStatusName());
	}

	@Test
	void testGetDeliveryStatusResponse_NullValues() {
		GetDeliveryStatusResponse response = new GetDeliveryStatusResponse(null, null);

		assertNull(response.deliveryStatusId());
		assertNull(response.deliveryStatusName());
	}

	@Test
	void testGetDeliveryStatusResponse_EmptyName() {
		Long id = 1L;
		String name = "";

		GetDeliveryStatusResponse response = new GetDeliveryStatusResponse(id, name);

		assertEquals(id, response.deliveryStatusId());
		assertEquals(name, response.deliveryStatusName());
	}
}
