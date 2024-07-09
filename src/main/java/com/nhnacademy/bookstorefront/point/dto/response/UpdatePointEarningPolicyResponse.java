package com.nhnacademy.bookstorefront.point.dto.response;

import java.math.BigDecimal;

public record UpdatePointEarningPolicyResponse(
	String pointEarningPolicyType,
	BigDecimal pointEarningAmount
) {

}
