package com.nhnacademy.bookstorefront.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.order.dto.response.FindByInfoIdBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.FindByInfoIdBookOrderGetOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.CancelResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.PaymentSaveResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.TransactionsResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.UpdatePaymentResponse;
import com.nhnacademy.bookstorefront.payment.feignclient.PaymentServiceClient;
import com.nhnacademy.bookstorefront.payment.service.impl.PaymentServiceImpl;

class PaymentServiceImplTest {

	@Mock
	private PaymentServiceClient paymentServiceClient;

	@InjectMocks
	private PaymentServiceImpl paymentService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSavePaymentResponse() {
		String paymentResponseJson = "{\"example\": \"data\"}";
		PaymentSaveResponse response = new PaymentSaveResponse("paymentKey123");

		when(paymentServiceClient.savePayment(any(String.class)))
			.thenReturn(ResponseEntity.ok(response));

		PaymentSaveResponse result = paymentService.savePaymentResponse(paymentResponseJson);

		assertEquals(response, result);
	}

	@Test
	void testFindByOrderInfoId() {
		String orderInfoId = "order123";
		GetBookOrderByInfoIdResponse response = new GetBookOrderByInfoIdResponse(
			1L,
			new FindByInfoIdBookOrderGetBookResponse("Book Title", BigDecimal.valueOf(100), "Book Description"),
			new FindByInfoIdBookOrderGetOrderResponse("order123", BigDecimal.valueOf(200), LocalDateTime.now(),
				BigDecimal.valueOf(10), BigDecimal.valueOf(5)),
			2,
			"Order Title"
		);

		when(paymentServiceClient.bookOrder(any(String.class)))
			.thenReturn(ResponseEntity.ok(response));

		GetBookOrderByInfoIdResponse result = paymentService.findByOrderInfoId(orderInfoId);

		assertEquals(response, result);
	}

	@Test
	void testFindByOrder() {
		String orderInfoId = "order123";
		GetOrderByInfoResponse response = GetOrderByInfoResponse.builder()
			.orderId(1L)
			.infoId("order123")
			.payername("John Doe")
			.payerEmail("john.doe@example.com")
			.payerAddress("123 Main St")
			.orderDate(LocalDateTime.now())
			.status("Completed")
			.price(BigDecimal.valueOf(200))
			.couponSale(BigDecimal.valueOf(10))
			.pointSale(BigDecimal.valueOf(5))
			.build();

		when(paymentServiceClient.findByOrderInfoId(any(String.class)))
			.thenReturn(ResponseEntity.ok(response));

		GetOrderByInfoResponse result = paymentService.findByOrder(orderInfoId);

		assertEquals(response, result);
	}

	@Test
	void testTransactions() {
		String paymentResponseJson = "{\"example\": \"data\"}";
		TransactionsResponse response = TransactionsResponse.from(
			"order123", BigDecimal.valueOf(200), "Completed", "Provider", "Order Name", LocalDateTime.now()
		);

		when(paymentServiceClient.transactions(any(String.class)))
			.thenReturn(ResponseEntity.ok(response));

		TransactionsResponse result = paymentService.transactions(paymentResponseJson);

		assertEquals(response, result);
	}

	@Test
	void testPaymentFindByOrderInfoId() {
		String orderInfoId = "order123";
		CancelResponse response = CancelResponse.from("paymentKey123", 1L);

		when(paymentServiceClient.cancel(any(String.class)))
			.thenReturn(ResponseEntity.ok(response));

		CancelResponse result = paymentService.paymentFindByOrderInfoId(orderInfoId);

		assertEquals(response, result);
	}

	@Test
	void testUpdatePayment() {
		String paymentResponseJson = "{\"example\": \"data\"}";
		Long paymentId = 1L;
		UpdatePaymentResponse response = UpdatePaymentResponse.from("Updated");

		when(paymentServiceClient.cancel(any(String.class), any(Long.class)))
			.thenReturn(ResponseEntity.ok(response));

		UpdatePaymentResponse result = paymentService.updatePayment(paymentResponseJson, paymentId);

		assertEquals(response, result);
	}
}
