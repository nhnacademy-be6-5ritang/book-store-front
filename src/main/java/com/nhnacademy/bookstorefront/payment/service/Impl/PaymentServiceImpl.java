package com.nhnacademy.bookstorefront.payment.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.CancelResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.PaymentSaveResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.TransactionsResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.UpdatePaymentResponse;
import com.nhnacademy.bookstorefront.payment.feignclient.PaymentServiceClient;
import com.nhnacademy.bookstorefront.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	private final PaymentServiceClient paymentServiceClient;

	//나중에 dto 사용 안할 시 삭제
	@Override
	public PaymentSaveResponse savePaymentResponse(String paymentResponseJson) {
		return paymentServiceClient.savePayment(paymentResponseJson).getBody();
	}

	@Override
	@Transactional(readOnly = true)
	public GetBookOrderByInfoIdResponse findByOrderInfoId(String orderInfoId) {
		return paymentServiceClient.bookOrder(orderInfoId).getBody();
	}

	@Override
	@Transactional(readOnly = true)
	public GetOrderByInfoResponse findByOrder(String orderInfoId) {
		return paymentServiceClient.findByOrderInfoId(orderInfoId).getBody();
	}

	@Override
	@Transactional(readOnly = true)
	public TransactionsResponse transactions(String paymentResponseJson) {
		return paymentServiceClient.transactions(paymentResponseJson).getBody();
	}

	@Override
	@Transactional(readOnly = true)
	public CancelResponse paymentFindByOrderInfoId(String orderInfoId) {
		return paymentServiceClient.cancel(orderInfoId).getBody();
	}

	//나중에 dto 사용 안할 시 삭제
	@Override
	public UpdatePaymentResponse updatePayment(String paymentResponseJson, Long paymentId) {
		return paymentServiceClient.cancel(paymentResponseJson, paymentId).getBody();
	}
}
