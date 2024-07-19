package com.nhnacademy.bookstorefront.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PaymentConfirmationRequest(
	@NotBlank @Size(max = 200) String paymentKey,
	@NotNull int amount,
	@NotBlank @Size(min = 6, max = 64) String orderId
) {
	public static PaymentConfirmationRequest form(String paymentKey, int amount, String orderId) {
		return PaymentConfirmationRequest.builder()
			.paymentKey(paymentKey)
			.amount(amount)
			.orderId(orderId)
			.build();
	}
}
