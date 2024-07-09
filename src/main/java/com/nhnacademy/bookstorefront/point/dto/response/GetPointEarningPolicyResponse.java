package com.nhnacademy.bookstorefront.point.dto.response;

import java.math.BigDecimal;

public record GetPointEarningPolicyResponse(
	Long pointEarningPolicyId,
	String pointEarningPolicyType,
	BigDecimal pointEarningAmount,
	String pointEarningPolicyStatus
) {
}
