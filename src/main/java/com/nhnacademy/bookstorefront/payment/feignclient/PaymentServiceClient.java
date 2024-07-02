package com.nhnacademy.bookstorefront.payment.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.PaymentSaveResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.TransactionsResponse;

@FeignClient(name = "payment-feign-service", url = "http://localhost:8083")
public interface PaymentServiceClient {
	@GetMapping("/payments/books-orders/{order_info_id}")
	ResponseEntity<GetBookOrderByInfoIdResponse> bookOrder(@PathVariable("order_info_id") String orderInfoId);

	@GetMapping("/payments/order-info/{order_info_id}")
	ResponseEntity<GetOrderByInfoResponse> findByOrderInfoId(@PathVariable("order_info_id") String orderInfoId);

	@PostMapping("/payments")
	ResponseEntity<PaymentSaveResponse> savePayment(@RequestBody String paymentResponseJson);

	@PostMapping("/payments/transactions")
	ResponseEntity<TransactionsResponse> transactions(@RequestBody String paymentResponseJson);
}
