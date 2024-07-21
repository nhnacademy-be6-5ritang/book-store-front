package com.nhnacademy.bookstorefront.order;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.OrderCheckNonRequest;
import com.nhnacademy.bookstorefront.order.dto.response.*;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.Impl.OrderServiceImpl;

public class OrderServiceImplTest {

	@Mock
	private OrderServiceClient orderServiceClient;

	@InjectMocks
	private OrderServiceImpl orderService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateOrder() {
		CreateOrderRequest request = new CreateOrderRequest(
			"John Doe",
			"john.doe@example.com",
			"1234567890",
			"123 Main St",
			new BigDecimal("100.00"),
			new BigDecimal("10.00"),
			new BigDecimal("5.00"),
			1L
		);

		CreateOrderResponse response = new CreateOrderResponse(
			1L,
			"info123",
			new BigDecimal("100.00"),
			LocalDateTime.now(),
			new BigDecimal("10.00"),
			new BigDecimal("5.00")
		);

		when(orderServiceClient.createOrder(any(CreateOrderRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		CreateOrderResponse result = orderService.createOrder(request);

		assertEquals(response, result);
	}

	@Test
	public void testFindAllUserId() {
		GetAllOrderResponse orderResponse = new GetAllOrderResponse(
			1L,
			LocalDateTime.now(),
			new BigDecimal("100.00"),
			"info123",
			"John Doe"
		);

		GetAllListOrderResponse response = new GetAllListOrderResponse(List.of(orderResponse));

		when(orderServiceClient.findAllByUserId())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderResponse result = orderService.findAllUserId();

		assertEquals(response, result);
	}

	@Test
	public void testFindByOrderInfoId() {
		String orderInfoId = "order123";
		GetOrderByInfoResponse response = mock(GetOrderByInfoResponse.class);

		when(orderServiceClient.findByOrderInfoId(orderInfoId))
			.thenReturn(ResponseEntity.ok(response));

		GetOrderByInfoResponse result = orderService.findByOrderInfoId(orderInfoId);

		assertEquals(response, result);
	}

	@Test
	public void testFindByOrderStatusWait() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusWait())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusWait();

		assertEquals(response, result);
	}

	@Test
	public void testFindByOrderStatusGoing() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusGoing())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusGoing();

		assertEquals(response, result);
	}

	@Test
	public void testFindByOrderStatusComplete() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusComplete())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusComplete();

		assertEquals(response, result);
	}

	@Test
	public void testFindByOrderStatusRefunded() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusRefunded())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusRefunded();

		assertEquals(response, result);
	}

	@Test
	public void testFindByOrderStatusRefunding() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusRefunding())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusRefunding();

		assertEquals(response, result);
	}

	@Test
	public void testFindByOrderInfoIdByEmail() {
		OrderCheckNonRequest request = mock(OrderCheckNonRequest.class);
		GetNonOrderByInfoResponse response = mock(GetNonOrderByInfoResponse.class);

		when(orderServiceClient.getOrderByInfoNon(any(OrderCheckNonRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		GetNonOrderByInfoResponse result = orderService.findByOrderInfoIdByEmail(request);

		assertEquals(response, result);
	}

	@Test
	public void testGetUserPoint() {
		GetUserPointOrderResponse response = mock(GetUserPointOrderResponse.class);

		when(orderServiceClient.getUserPointOrders())
			.thenReturn(ResponseEntity.ok(response));

		GetUserPointOrderResponse result = orderService.getUserPoint();

		assertEquals(response, result);
	}

	@Test
	public void testRefundedOrder() {
		String orderInfoId = "order123";

		when(orderServiceClient.refundedOrder(orderInfoId))
			.thenReturn(ResponseEntity.ok().build());

		orderService.refundedOrder(orderInfoId);

	}

	@Test
	public void testRefundingOrder() {
		String orderInfoId = "order123";

		when(orderServiceClient.refundingOrder(orderInfoId))
			.thenReturn(ResponseEntity.ok().build());

		orderService.refundingOrder(orderInfoId);

	}

	@Test
	public void testCreateCartOrder() {
		CreateCartOrderResponse response = mock(CreateCartOrderResponse.class);

		when(orderServiceClient.createCartOrders())
			.thenReturn(ResponseEntity.ok(response));

		CreateCartOrderResponse result = orderService.createCartOrder();

		assertEquals(response, result);
	}

	@Test
	public void testUpdateCartOrder() {
		CreateOrderRequest request = mock(CreateOrderRequest.class);
		Long orderId = 1L;
		CreateOrderResponse response = mock(CreateOrderResponse.class);

		when(orderServiceClient.updateCartOrder(any(CreateOrderRequest.class), any(Long.class)))
			.thenReturn(ResponseEntity.ok(response));

		CreateOrderResponse result = orderService.updateCartOrder(request, orderId);

		assertEquals(response, result);
	}
}
