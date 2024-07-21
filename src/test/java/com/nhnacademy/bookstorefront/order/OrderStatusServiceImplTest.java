package com.nhnacademy.bookstorefront.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderStatusRequest;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderStatusResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.Impl.OrderStatusServiceImpl;

public class OrderStatusServiceImplTest {

	@Mock
	private OrderServiceClient orderServiceClient;

	@InjectMocks
	private OrderStatusServiceImpl orderStatusService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateOrderStatus() {
		CreateOrderStatusRequest request = mock(CreateOrderStatusRequest.class);

		GetOrderStatusResponse response = mock(GetOrderStatusResponse.class);

		when(orderServiceClient.createOrderStatus(any(CreateOrderStatusRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		GetOrderStatusResponse result = orderStatusService.create(request);

		assertEquals(response, result);
	}

	@Test
	public void testUpdateOrderStatus() {
		CreateOrderStatusRequest request = mock(CreateOrderStatusRequest.class);
		Long id = 1L;

		GetOrderStatusResponse response = mock(GetOrderStatusResponse.class);

		when(orderServiceClient.updateOrderStatus(any(Long.class), any(CreateOrderStatusRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		GetOrderStatusResponse result = orderStatusService.update(request, id);

		assertEquals(response, result);
	}

	@Test
	public void testDeleteOrderStatus() {
		Long id = 1L;

		when(orderServiceClient.deleteOrderStatus(id)).thenReturn(null);

		orderStatusService.delete(id);

		verify(orderServiceClient, times(1)).deleteOrderStatus(id);
	}

	@Test
	public void testFindAllOrderStatuses() {
		GetOrderStatusResponse statusResponse = mock(GetOrderStatusResponse.class);

		List<GetOrderStatusResponse> responseList = List.of(statusResponse);

		when(orderServiceClient.orderStatusAll())
			.thenReturn(responseList);

		List<GetOrderStatusResponse> result = orderStatusService.findAll();

		assertEquals(responseList, result);
	}
}
