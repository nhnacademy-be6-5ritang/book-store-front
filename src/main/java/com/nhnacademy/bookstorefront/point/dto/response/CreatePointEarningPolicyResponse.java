package com.nhnacademy.bookstorefront.point.dto.response;

import java.math.BigDecimal;

public record CreatePointEarningPolicyResponse(
	String pointEarningPolicyType,
	BigDecimal pointEarningAmount
) {
}
