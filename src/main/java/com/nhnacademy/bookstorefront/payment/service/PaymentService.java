package com.nhnacademy.bookstorefront.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.PaymentSaveResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.TransactionsResponse;
import com.nhnacademy.bookstorefront.payment.feignclient.PaymentServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentServiceClient paymentServiceClient;

	//나중에 dto 사용 안할 시 삭제
	public PaymentSaveResponse savePaymentResponse(String paymentResponseJson) {
		return paymentServiceClient.savePayment(paymentResponseJson).getBody();
	}

	@Transactional(readOnly = true)
	public GetBookOrderByInfoIdResponse findByOrderInfoId(String orderInfoId) {
		return paymentServiceClient.bookOrder(orderInfoId).getBody();
	}

	@Transactional(readOnly = true)
	public GetOrderByInfoResponse findByOrder(String orderInfoId) {
		return paymentServiceClient.findByOrderInfoId(orderInfoId).getBody();
	}

	@Transactional(readOnly = true)
	public TransactionsResponse transactions(String paymentResponseJson) {
		return paymentServiceClient.transactions(paymentResponseJson).getBody();
	}
}
