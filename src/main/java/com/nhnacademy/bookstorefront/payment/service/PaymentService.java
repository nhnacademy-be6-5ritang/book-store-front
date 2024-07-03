package com.nhnacademy.bookstorefront.payment.service;

import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.CancelResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.GetBookOrderByInfoIdResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.PaymentSaveResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.TransactionsResponse;
import com.nhnacademy.bookstorefront.payment.dto.response.UpdatePaymentResponse;

public interface PaymentService {
	PaymentSaveResponse savePaymentResponse(String paymentResponseJson);

	TransactionsResponse transactions(String paymentResponseJson);

	GetBookOrderByInfoIdResponse findByOrderInfoId(String orderInfoId);

	GetOrderByInfoResponse findByOrder(String orderInfoId);

	CancelResponse paymentFindByOrderInfoId(String orderInfoId);

	UpdatePaymentResponse updatePayment(String paymentResponseJson, Long paymentId);

}