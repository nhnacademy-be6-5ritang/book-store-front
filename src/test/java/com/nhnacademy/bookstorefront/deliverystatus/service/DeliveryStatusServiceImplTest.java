package com.nhnacademy.bookstorefront.deliverystatus.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.deliverystatus.dto.request.CreateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.request.UpdateDeliveryStatusRequest;
import com.nhnacademy.bookstorefront.deliverystatus.dto.response.GetDeliveryStatusResponse;
import com.nhnacademy.bookstorefront.deliverystatus.feignclient.DeliveryStatusServiceClient;
import com.nhnacademy.bookstorefront.deliverystatus.service.impl.DeliveryStatusServiceImpl;

class DeliveryStatusServiceImplTest {

	@Mock
	private DeliveryStatusServiceClient deliveryStatusServiceClient;

	@InjectMocks
	private DeliveryStatusServiceImpl deliveryStatusService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetDeliveryStatus() {
		Long deliveryStatusId = 1L;
		GetDeliveryStatusResponse response = new GetDeliveryStatusResponse(deliveryStatusId, "Delivered");

		when(deliveryStatusServiceClient.getDeliveryStatus(anyLong())).thenReturn(ResponseEntity.ok(response));

		GetDeliveryStatusResponse result = deliveryStatusService.getDeliveryStatus(deliveryStatusId);

		assertNotNull(result);
		assertEquals(deliveryStatusId, result.deliveryStatusId());
		assertEquals("Delivered", result.deliveryStatusName());
	}

	@Test
	void testGetDeliveryStatuses() {
		GetDeliveryStatusResponse response = new GetDeliveryStatusResponse(1L, "Delivered");
		List<GetDeliveryStatusResponse> responseList = List.of(response);

		when(deliveryStatusServiceClient.getDeliveryStatuses()).thenReturn(ResponseEntity.ok(responseList));

		List<GetDeliveryStatusResponse> result = deliveryStatusService.getDeliveryStatuses();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Delivered", result.get(0).deliveryStatusName());
	}

	@Test
	void testCreateDeliveryStatus() {
		CreateDeliveryStatusRequest request = new CreateDeliveryStatusRequest("Pending");

		deliveryStatusService.createDeliveryStatus(request);

		verify(deliveryStatusServiceClient, times(1)).createDeliveryStatus(any(CreateDeliveryStatusRequest.class));
	}

	@Test
	void testUpdateDeliveryStatus() {
		Long deliveryStatusId = 1L;
		UpdateDeliveryStatusRequest request = new UpdateDeliveryStatusRequest("In Transit");

		deliveryStatusService.updateDeliveryStatus(deliveryStatusId, request);

		verify(deliveryStatusServiceClient, times(1)).updateDeliveryStatus(anyLong(),
			any(UpdateDeliveryStatusRequest.class));
	}

	@Test
	void testDeleteDeliveryStatus() {
		Long deliveryStatusId = 1L;

		deliveryStatusService.deleteDeliveryStatus(deliveryStatusId);

		verify(deliveryStatusServiceClient, times(1)).deleteDeliveryStatus(anyLong());
	}
}