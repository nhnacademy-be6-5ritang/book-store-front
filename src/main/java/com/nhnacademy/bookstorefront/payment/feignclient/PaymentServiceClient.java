package com.nhnacademy.bookstorefront.payment.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.CancelResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.PaymentSaveResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.TransactionsResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.UpdatePaymentResponse;

@FeignClient(name = "payment-feign-service", url = "http://localhost:8083")
public interface PaymentServiceClient {
	@GetMapping("/api/payments/books-orders/{order_info_id}")
	ResponseEntity<GetBookOrderByInfoIdResponse> bookOrder(@PathVariable("order_info_id") String orderInfoId);

	@GetMapping("/api/payments/order-info/{order_info_id}")
	ResponseEntity<GetOrderByInfoResponse> findByOrderInfoId(@PathVariable("order_info_id") String orderInfoId);

	@PostMapping("/api/payments")
	ResponseEntity<PaymentSaveResponse> savePayment(@RequestBody String paymentResponseJson);

	@PostMapping("/api/payments/transactions")
	ResponseEntity<TransactionsResponse> transactions(@RequestBody String paymentResponseJson);

	@GetMapping("/api/payments/cancel/{order_info_id}")
	ResponseEntity<CancelResponse> cancel(@PathVariable("order_info_id") String orderInfoId);

	@PostMapping("/api/payments/cancel/test/{payment_id}")
	ResponseEntity<UpdatePaymentResponse> cancel(@RequestBody String paymentResponseJson, @PathVariable("payment_id") Long paymentId);
}
