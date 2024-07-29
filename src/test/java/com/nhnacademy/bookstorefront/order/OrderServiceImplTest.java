package com.nhnacademy.bookstorefront.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.OrderCheckNonRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateCartOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetNonOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetUserPointOrderResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.Impl.OrderServiceImpl;
import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;

class OrderServiceImplTest {

	@Mock
	private OrderServiceClient orderServiceClient;

	@InjectMocks
	private OrderServiceImpl orderService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateOrder() {
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
	void testFindAllUserId() {
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
	void testFindByOrderInfoId() {
		String orderInfoId = "order123";
		GetOrderByInfoResponse response = mock(GetOrderByInfoResponse.class);

		when(orderServiceClient.findByOrderInfoId(orderInfoId))
			.thenReturn(ResponseEntity.ok(response));

		GetOrderByInfoResponse result = orderService.findByOrderInfoId(orderInfoId);

		assertEquals(response, result);
	}

	@Test
	void testFindByOrderStatusWait() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusWait())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusWait();

		assertEquals(response, result);
	}

	@Test
	void testFindByOrderStatusGoing() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusGoing())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusGoing();

		assertEquals(response, result);
	}

	@Test
	void testFindByOrderStatusComplete() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusComplete())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusComplete();

		assertEquals(response, result);
	}

	@Test
	void testFindByOrderStatusRefunded() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusRefunded())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusRefunded();

		assertEquals(response, result);
	}

	@Test
	void testFindByOrderStatusRefunding() {
		GetAllListOrderByStatusResponse response = mock(GetAllListOrderByStatusResponse.class);

		when(orderServiceClient.getOrderStatusRefunding())
			.thenReturn(ResponseEntity.ok(response));

		GetAllListOrderByStatusResponse result = orderService.findByOrderStatusRefunding();

		assertEquals(response, result);
	}

	@Test
	void testFindByOrderInfoIdByEmail() {
		OrderCheckNonRequest request = mock(OrderCheckNonRequest.class);
		GetNonOrderByInfoResponse response = mock(GetNonOrderByInfoResponse.class);

		when(orderServiceClient.getOrderByInfoNon(any(OrderCheckNonRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		GetNonOrderByInfoResponse result = orderService.findByOrderInfoIdByEmail(request);

		assertEquals(response, result);
	}

	@Test
	void testGetUserPoint() {
		GetUserPointOrderResponse response = mock(GetUserPointOrderResponse.class);

		when(orderServiceClient.getUserPointOrders())
			.thenReturn(ResponseEntity.ok(response));

		GetUserPointOrderResponse result = orderService.getUserPoint();

		assertEquals(response, result);
	}

	@Test
	void testRefundedOrder() {
		String orderInfoId = "order123";

		when(orderServiceClient.refundedOrder(orderInfoId))
			.thenReturn(ResponseEntity.ok().build());

		orderService.refundedOrder(orderInfoId);

	}

	@Test
	void testRefundingOrder() {
		String orderInfoId = "order123";

		when(orderServiceClient.refundingOrder(orderInfoId))
			.thenReturn(ResponseEntity.ok().build());

		orderService.refundingOrder(orderInfoId);

	}

	@Test
	void testCreateCartOrder() {
		CreateCartOrderResponse response = mock(CreateCartOrderResponse.class);

		when(orderServiceClient.createCartOrders())
			.thenReturn(ResponseEntity.ok(response));

		CreateCartOrderResponse result = orderService.createCartOrder();

		assertEquals(response, result);
	}

	@Test
	void testUpdateCartOrder() {
		CreateOrderRequest request = mock(CreateOrderRequest.class);
		Long orderId = 1L;
		CreateOrderResponse response = mock(CreateOrderResponse.class);

		when(orderServiceClient.updateCartOrder(any(CreateOrderRequest.class), any(Long.class)))
			.thenReturn(ResponseEntity.ok(response));

		CreateOrderResponse result = orderService.updateCartOrder(request, orderId);

		assertEquals(response, result);
	}

	@Test
	void testGetMyUserInfoByOrder() {
		GetMyUserInfoResponse response = mock(GetMyUserInfoResponse.class);

		when(orderServiceClient.getMyUserInfoByInfo())
			.thenReturn(ResponseEntity.ok(response));

		GetMyUserInfoResponse result = orderService.getMyUserInfoByOrder();

		assertEquals(response, result);
	}

	@Test
	void testFindAllPageByUserId() {
		Page<GetAllOrderResponse> response = mock(Page.class);

		when(orderServiceClient.findAllPageByUserId(any(Pageable.class)))
			.thenReturn(ResponseEntity.ok(response));

		Pageable pageable = PageRequest.of(0, 10);
		Page<GetAllOrderResponse> result = orderService.findAllPageByUserId(pageable);

		assertEquals(response, result);
	}
}
