package com.nhnacademy.bookstorefront.point.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetPointTransactionResponse(
	String pointEarningPolicyType,
	BigDecimal pointTransactionAmount,
	LocalDateTime pointTransactionDate
) {
}
