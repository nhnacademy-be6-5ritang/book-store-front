package com.nhnacademy.bookstorefront.payment;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.nhnacademy.bookstorefront.bookcart.service.impl.BookCartServiceImpl;
import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;
import com.nhnacademy.bookstorefront.order.dto.response.FindByInfoIdBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.FindByInfoIdBookOrderGetOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.controller.PaymentController;
import com.nhnacademy.bookstorefront.payment.dto.request.CancelTextRequest;
import com.nhnacademy.bookstorefront.payment.dto.request.PaymentConfirmationRequest;
import com.nhnacademy.bookstorefront.payment.dto.response.CancelResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.TransactionsResponse;
import com.nhnacademy.bookstorefront.payment.service.impl.PaymentServiceImpl;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private PaymentServiceImpl paymentServiceImpl;

	@MockBean
	private BookCartServiceImpl bookCartServiceImpl;

	@MockBean
	private CacheServiceImpl cacheService;

	@MockBean
	private RestTemplate paymentRestTemplate;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(
			new PaymentController(paymentServiceImpl, paymentRestTemplate, bookCartServiceImpl)).build();
	}

	@Test
	void testPayment() throws Exception {
		String orderInfoId = "order123";
		GetOrderByInfoResponse orderInfoResponse = GetOrderByInfoResponse.builder()
			.orderId(1L)
			.infoId(orderInfoId)
			.payername("John Doe")
			.payerEmail("john.doe@example.com")
			.payerAddress("123 Main St")
			.orderDate(LocalDateTime.now())
			.status("Completed")
			.price(BigDecimal.valueOf(100))
			.couponSale(BigDecimal.valueOf(10))
			.pointSale(BigDecimal.valueOf(5))
			.build();

		GetBookOrderByInfoIdResponse bookOrderResponse = new GetBookOrderByInfoIdResponse(
			1L,
			new FindByInfoIdBookOrderGetBookResponse("Book Title", BigDecimal.valueOf(100), "Book Description"),
			new FindByInfoIdBookOrderGetOrderResponse(orderInfoId, BigDecimal.valueOf(200), LocalDateTime.now(),
				BigDecimal.valueOf(10), BigDecimal.valueOf(5)),
			2,
			"Order Title"
		);

		when(paymentServiceImpl.findByOrder(orderInfoId)).thenReturn(orderInfoResponse);
		when(paymentServiceImpl.findByOrderInfoId(orderInfoId)).thenReturn(bookOrderResponse);

		mockMvc.perform(get("/api/payments/{order_info_id}", orderInfoId))
			.andExpect(status().isOk())
			.andExpect(view().name("toss/toss"))
			.andExpect(model().attributeExists("orderName"))
			.andExpect(model().attributeExists("orderId"))
			.andExpect(model().attributeExists("price"))
			.andExpect(model().attributeExists("payerName"));
	}

	@Test
	void testPaymentSuccess() throws Exception {
		String orderId = "order123";
		String paymentKey = "paymentKey123";
		String amount = "100";
		String responseJson = "{\"example\": \"data\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic dGVzdF9za19BUTkyeW14TjM0MjllTUtFSmVManJhalJLWHZkOg==");

		PaymentConfirmationRequest request = PaymentConfirmationRequest.form(paymentKey, Integer.parseInt(amount),
			orderId);
		HttpEntity<PaymentConfirmationRequest> entity = new HttpEntity<>(request, headers);

		when(paymentRestTemplate.postForObject(anyString(), any(HttpEntity.class), any(Class.class)))
			.thenReturn(responseJson);

		when(paymentServiceImpl.savePaymentResponse(responseJson)).thenReturn(null);

		mockMvc.perform(get("/api/payments/success")
				.param("orderId", orderId)
				.param("paymentKey", paymentKey)
				.param("amount", amount))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/complete/" + orderId));
	}

	@Test
	void testPaymentFail() throws Exception {
		mockMvc.perform(get("/api/payments/fail"))
			.andExpect(status().isOk())
			.andExpect(view().name("toss/fail"));
	}

	@Test
	void testPaymentTransactions() throws Exception {
		String orderInfoId = "order123";
		String responseJson = "{\"example\": \"data\"}";

		GetOrderByInfoResponse orderInfoResponse = GetOrderByInfoResponse.builder()
			.orderId(1L)
			.infoId(orderInfoId)
			.status("Completed")
			.build();

		TransactionsResponse transactionsResponse = TransactionsResponse.from(
			orderInfoId, BigDecimal.valueOf(200), "Completed", "Provider", "Order Name", LocalDateTime.now()
		);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic dGVzdF9za19BUTkyeW14TjM0MjllTUtFSmVManJhalJLWHZkOg==");
		HttpEntity<String> entity = new HttpEntity<>(headers);

		when(paymentRestTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
			.thenReturn(ResponseEntity.ok(responseJson));
		when(paymentServiceImpl.transactions(responseJson)).thenReturn(transactionsResponse);
		when(paymentServiceImpl.findByOrder(orderInfoId)).thenReturn(orderInfoResponse);

		mockMvc.perform(get("/api/payments/transactions/{order_info_id}", orderInfoId))
			.andExpect(status().isOk())
			.andExpect(view().name("toss/transactions"))
			.andExpect(model().attributeExists("paymentInfo"))
			.andExpect(model().attributeExists("orderStatus"));
	}

	@Test
	void testPaymentCancel() throws Exception {
		String orderInfoId = "order123";

		mockMvc.perform(get("/api/payments/cancel/{order_info_id}", orderInfoId))
			.andExpect(status().isOk())
			.andExpect(view().name("toss/cancel"))
			.andExpect(model().attributeExists("orderInfoId"))
			.andExpect(model().attribute("orderInfoId", orderInfoId));
	}

	@Test
	void testPaymentCancelPost() throws Exception {
		String orderInfoId = "order123";
		String responseJson = "{\"example\": \"data\"}";
		CancelTextRequest cancelTextRequest = new CancelTextRequest("Reason");

		CancelResponse cancelResponse = CancelResponse.from("paymentKey123", 1L);
		TransactionsResponse transactionsResponse = TransactionsResponse.from(
			orderInfoId, BigDecimal.valueOf(200), "Completed", "Provider", "Order Name", LocalDateTime.now()
		);

		when(paymentServiceImpl.paymentFindByOrderInfoId(orderInfoId)).thenReturn(cancelResponse);
		when(paymentRestTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
			.thenReturn(ResponseEntity.ok(responseJson));
		when(paymentServiceImpl.updatePayment(anyString(), anyLong())).thenReturn(null);
		when(paymentServiceImpl.transactions(responseJson)).thenReturn(transactionsResponse);

		mockMvc.perform(post("/api/payments/cancel/{order_info_id}", orderInfoId)
				.flashAttr("cancelTextRequest", cancelTextRequest))
			.andExpect(status().isOk())
			.andExpect(view().name("toss/transactions"))
			.andExpect(model().attributeExists("paymentInfo"));
	}
}
