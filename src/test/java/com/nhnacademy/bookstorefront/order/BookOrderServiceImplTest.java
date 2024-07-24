package com.nhnacademy.bookstorefront.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderGetOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.UpdateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.Impl.BookOrderServiceImpl;

public class BookOrderServiceImplTest {

	@Mock
	private OrderServiceClient orderServiceClient;

	@InjectMocks
	private BookOrderServiceImpl bookOrderService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateBookOrder() {
		CreateBookOrderRequest request = new CreateBookOrderRequest(1L, 1L, 2);
		CreateBookOrderGetBookResponse getBookResponse = new CreateBookOrderGetBookResponse(
			"Book Title",
			new BigDecimal("29.99"),
			"Book Description"
		);
		CreateBookOrderGetOrderResponse getOrderResponse = new CreateBookOrderGetOrderResponse(
			"info123",
			new BigDecimal("59.98"),
			LocalDateTime.now(),
			new BigDecimal("5.00"),
			new BigDecimal("2.00")
		);
		CreateBookOrderResponse response = new CreateBookOrderResponse(getBookResponse, getOrderResponse, 2, 1L);

		when(orderServiceClient.createBookOrder(any(CreateBookOrderRequest.class)))
			.thenReturn(ResponseEntity.ok(response));

		CreateBookOrderResponse result = bookOrderService.createBookOrder(request);

		assertEquals(response, result);
	}

	@Test
	public void testUpdateOrder() {
		Long bookOrderId = 1L;
		Long orderId = 2L;
		UpdateBookOrderResponse response = new UpdateBookOrderResponse(
			"info123",
			LocalDateTime.now(),
			new BigDecimal("59.98"),
			"Book Title",
			new BigDecimal("29.99"),
			2,
			1L
		);

		when(orderServiceClient.updateBookOrder(bookOrderId, orderId))
			.thenReturn(ResponseEntity.ok(response));

		UpdateBookOrderResponse result = bookOrderService.updateOrder(bookOrderId, orderId);

		assertEquals(response, result);
	}

	@Test
	public void testGetBookOrder() {
		Long bookOrderId = 1L;
		GetBookOrderGetBookResponse getBookResponse = new GetBookOrderGetBookResponse(
			"Book Title",
			"Book Title",
			new BigDecimal("29.99"),
			"Book Description",
			1L
		);
		GetBookOrderResponse response = new GetBookOrderResponse(getBookResponse, 2, 1L, 2L);

		when(orderServiceClient.getBookOrder(bookOrderId))
			.thenReturn(ResponseEntity.ok(response));

		GetBookOrderResponse result = bookOrderService.getBookOrder(bookOrderId);

		assertEquals(response, result);
	}

	@Test
	public void testGetBookOrderByOrderId() {
		String orderInfoId = "order123";
		GetBookOrderGetBookResponse getBookResponse1 = new GetBookOrderGetBookResponse(
			"Book Title 1",
			"Book Title 1",
			new BigDecimal("19.99"),
			"Book Description 1",
			1L
		);
		GetBookOrderGetBookResponse getBookResponse2 = new GetBookOrderGetBookResponse(
			"Book Title 2",
			"Book Title 2",
			new BigDecimal("29.99"),
			"Book Description 2",
			2L
		);
		GetBookOrderResponse response1 = new GetBookOrderResponse(getBookResponse1, 1, 1L, 1L);
		GetBookOrderResponse response2 = new GetBookOrderResponse(getBookResponse2, 2, 2L, 2L);
		List<GetBookOrderResponse> responseList = Arrays.asList(response1, response2);

		when(orderServiceClient.getCartOrder(orderInfoId))
			.thenReturn(ResponseEntity.ok(responseList));

		List<GetBookOrderResponse> result = bookOrderService.getBookOrderByOrderId(orderInfoId);

		assertEquals(responseList, result);
	}
}
