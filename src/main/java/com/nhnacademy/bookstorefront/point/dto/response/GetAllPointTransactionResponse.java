package com.nhnacademy.bookstorefront.point.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetAllPointTransactionResponse(
	Long userId,
	Long policyId,
	BigDecimal amount,
	LocalDateTime date
) {

}
