package com.nhnacademy.bookstorefront.payment.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
	String paymentKey,
	String orderId,
	BigDecimal amount,
	String status,
	LocalDateTime date
) {

}
