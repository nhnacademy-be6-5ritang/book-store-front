package com.nhnacademy.bookstorefront.point.dto.request;

import java.math.BigDecimal;

public record CreatePointEarningPolicyRequest(
	String pointEarningPolicyType,
	BigDecimal pointEarningAmount
) {
}
