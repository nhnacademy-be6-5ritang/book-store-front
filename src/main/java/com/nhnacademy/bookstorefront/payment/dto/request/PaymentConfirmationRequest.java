package com.nhnacademy.bookstorefront.payment.dto.request;

import lombok.Builder;

@Builder
public record PaymentConfirmationRequest(
	String paymentKey,
	int amount,
	String orderId
) {
	public static PaymentConfirmationRequest form(String paymentKey, int amount, String orderId) {
		return PaymentConfirmationRequest.builder()
			.paymentKey(paymentKey)
			.amount(amount)
			.orderId(orderId)
			.build();
	}
}
