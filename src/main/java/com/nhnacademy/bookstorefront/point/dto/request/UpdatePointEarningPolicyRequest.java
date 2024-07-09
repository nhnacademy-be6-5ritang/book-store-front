package com.nhnacademy.bookstorefront.point.dto.request;

import java.math.BigDecimal;

public record UpdatePointEarningPolicyRequest(
	String pointEarningPolicyType,
	BigDecimal pointEarningAmount
) {
}
