package com.nhnacademy.bookstorefront.deliverystatus.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DeliveryStatusRequestTest {

	@Test
	void testCreateDeliveryStatusRequest() {
		String name = "Pending";

		CreateDeliveryStatusRequest request = new CreateDeliveryStatusRequest(name);

		assertEquals(name, request.deliveryStatusName());
	}

	@Test
	void testCreateDeliveryStatusRequest_NullValue() {
		CreateDeliveryStatusRequest request = new CreateDeliveryStatusRequest(null);

		assertNull(request.deliveryStatusName());
	}

	@Test
	void testCreateDeliveryStatusRequest_EmptyName() {
		String name = "";
		CreateDeliveryStatusRequest request = new CreateDeliveryStatusRequest(name);

		assertEquals(name, request.deliveryStatusName());
	}

	@Test
	void testCreateDeliveryStatusRequest_TooLongName() {
		String name = "ThisNameIsTooLong";
		CreateDeliveryStatusRequest request = new CreateDeliveryStatusRequest(name);

		assertEquals(name, request.deliveryStatusName());
	}

	@Test
	void testUpdateDeliveryStatusRequest() {
		String name = "Shipped";

		UpdateDeliveryStatusRequest request = new UpdateDeliveryStatusRequest(name);

		assertEquals(name, request.deliveryStatusName());
	}

	@Test
	void testUpdateDeliveryStatusRequest_NullValue() {
		UpdateDeliveryStatusRequest request = new UpdateDeliveryStatusRequest(null);

		assertNull(request.deliveryStatusName());
	}

	@Test
	void testUpdateDeliveryStatusRequest_EmptyName() {
		String name = "";
		UpdateDeliveryStatusRequest request = new UpdateDeliveryStatusRequest(name);

		assertEquals(name, request.deliveryStatusName());
	}

	@Test
	void testUpdateDeliveryStatusRequest_TooLongName() {
		String name = "ThisNameIsTooLong";
		UpdateDeliveryStatusRequest request = new UpdateDeliveryStatusRequest(name);

		assertEquals(name, request.deliveryStatusName());
	}
}