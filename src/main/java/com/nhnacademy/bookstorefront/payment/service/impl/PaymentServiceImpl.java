package com.nhnacademy.bookstorefront.payment.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.CancelResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.PaymentResponse;
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

	/**
	 *{@inheritDoc}
	 */
	@Override
	public PaymentSaveResponse savePaymentResponse(String paymentResponseJson) {
		return paymentServiceClient.savePayment(paymentResponseJson).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public GetBookOrderByInfoIdResponse findByOrderInfoId(String orderInfoId) {
		return paymentServiceClient.bookOrder(orderInfoId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public GetOrderByInfoResponse findByOrder(String orderInfoId) {
		return paymentServiceClient.findByOrderInfoId(orderInfoId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public TransactionsResponse transactions(String paymentResponseJson) {
		return paymentServiceClient.transactions(paymentResponseJson).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public CancelResponse paymentFindByOrderInfoId(String orderInfoId) {
		return paymentServiceClient.cancel(orderInfoId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public UpdatePaymentResponse updatePayment(String paymentResponseJson, Long paymentId) {
		return paymentServiceClient.cancel(paymentResponseJson, paymentId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void savePointSalePayment(String orderInfoId) {
		paymentServiceClient.pointSale(orderInfoId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void cancelPointSalePayment(Long paymentId) {
		paymentServiceClient.cancelPointSale(paymentId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public PaymentResponse getPayment(String orderInfoId) {
		return paymentServiceClient.pointSaleInfo(orderInfoId).getBody();
	}
}
