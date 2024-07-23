package com.nhnacademy.bookstorefront.delivery;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.delivery.dto.request.CreateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryByOrderIdRequest;
import com.nhnacademy.bookstorefront.delivery.dto.request.UpdateDeliveryRequest;
import com.nhnacademy.bookstorefront.delivery.dto.response.CreateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryAddOrderPolicyResponse;
import com.nhnacademy.bookstorefront.delivery.dto.response.UpdateDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.feignclient.DeliveryServiceClient;
import com.nhnacademy.bookstorefront.delivery.service.impl.DeliveryServiceImpl;

public class DeliveryServiceImplTest {

	@Mock
	private DeliveryServiceClient deliveryServiceClient;

	@InjectMocks
	private DeliveryServiceImpl deliveryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// @Test
	// void testGetDeliveriesByUserId() {
	// 	// Given
	// 	Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "deliveryReceiverDate"));
	// 	GetDeliveriesRequest request = new GetDeliveriesRequest(1L);
	// 	GetDeliveryResponse response = new GetDeliveryResponse(
	// 		"Sender Name", "Sender Phone", LocalDateTime.now(), "Sender Address",
	// 		"Receiver", "Receiver Phone", LocalDateTime.now(), "Receiver Address",
	// 		1L, "Delivered"
	// 	);
	// 	List<GetDeliveryResponse> responses = List.of(response);
	// 	Page<GetDeliveryResponse> expectedPage = new PageImpl<>(responses, pageable, responses.size());
	//
	// 	when(deliveryServiceClient.getDeliveriesByUserId(anyInt(), anyInt(), anyString(), any()))
	// 		.thenReturn(ResponseEntity.ok(expectedPage));
	//
	// 	Page<GetDeliveryResponse> result = deliveryService.getDeliveriesByUserId(0, 10, "asc", request);
	//
	// 	assertEquals(expectedPage, result);
	// }

	@Test
	void testGetDelivery() {
		Long deliveryId = 1L;
		GetDeliveryResponse expectedResponse = new GetDeliveryResponse(1L,
			"Sender Name", "Sender Phone", LocalDateTime.now(), "Sender Address",
			"Receiver", "Receiver Phone", LocalDateTime.now(), "Receiver Address",
			1L, "Delivered"
		);
		when(deliveryServiceClient.getDelivery(anyLong())).thenReturn(ResponseEntity.ok(expectedResponse));

		GetDeliveryResponse result = deliveryService.getDelivery(deliveryId);

		assertEquals(expectedResponse, result);
	}

	@Test
	void testCreateDelivery() {
		CreateDeliveryRequest request = new CreateDeliveryRequest(
			"Receiver", "Receiver Phone", LocalDateTime.now(), "Receiver Address", "Receiver Address 2"
		);
		CreateDeliveryResponse expectedResponse = new CreateDeliveryResponse(
			1L, "Sender Name", "Sender Phone", LocalDateTime.now(), "Sender Address",
			"Receiver", "Receiver Phone", LocalDateTime.now(), "Receiver Address",
			1L, 1L
		);
		when(deliveryServiceClient.createDelivery(any())).thenReturn(ResponseEntity.ok(expectedResponse));

		CreateDeliveryResponse result = deliveryService.createDelivery(request);

		assertEquals(expectedResponse, result);
	}

	@Test
	void testUpdateDelivery() {
		// Given
		Long deliveryId = 1L;
		UpdateDeliveryRequest request = new UpdateDeliveryRequest(1L);
		UpdateDeliveryResponse expectedResponse = new UpdateDeliveryResponse(
			deliveryId, 1L, "Delivered"
		);
		when(deliveryServiceClient.updateDelivery(anyLong(), any())).thenReturn(ResponseEntity.ok(expectedResponse));

		UpdateDeliveryResponse result = deliveryService.updateDelivery(deliveryId, request);

		assertEquals(expectedResponse, result);
	}

	@Test
	void testDeleteDelivery() {
		Long deliveryId = 1L;
		deliveryService.deleteDelivery(deliveryId);
	}

	@Test
	void testUpdateDeliveryAddOrder() {
		// Given
		Long deliveryId = 1L;
		Long orderId = 1L;
		UpdateDeliveryAddOrderPolicyResponse expectedResponse = new UpdateDeliveryAddOrderPolicyResponse(deliveryId);
		when(deliveryServiceClient.addOrder(anyLong(), anyLong())).thenReturn(ResponseEntity.ok(expectedResponse));

		UpdateDeliveryAddOrderPolicyResponse result = deliveryService.updateDeliveryAddOrder(deliveryId, orderId);

		assertEquals(expectedResponse, result);
	}

	@Test
	void testGetDeliveryByOrderId() {
		// Given
		Long orderId = 1L;
		GetDeliveryResponse expectedResponse = new GetDeliveryResponse(
			1L, "Sender Name", "Sender Phone", LocalDateTime.now(), "Sender Address",
			"Receiver", "Receiver Phone", LocalDateTime.now(), "Receiver Address",
			orderId, "Delivered"
		);
		when(deliveryServiceClient.getDeliveryByOrder(anyLong())).thenReturn(ResponseEntity.ok(expectedResponse));

		GetDeliveryResponse result = deliveryService.getDeliveryByOrderId(orderId);

		assertEquals(expectedResponse, result);
	}

	@Test
	void testUpdateDeliveryByOrderId() {
		// Given
		Long orderId = 1L;
		UpdateDeliveryByOrderIdRequest request = new UpdateDeliveryByOrderIdRequest(
			"Sender Name", "Sender Address", "Sender Address 2", "Sender Phone"
		);

		deliveryService.updateDeliveryByOrderId(orderId, request);

	}
}
