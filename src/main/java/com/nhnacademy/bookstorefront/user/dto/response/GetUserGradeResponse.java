package com.nhnacademy.bookstorefront.user.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record GetUserGradeResponse(
	String userGradeName,
	BigDecimal userGradeMinAmount,
	BigDecimal userGradeMaxAmount,
	BigDecimal userGradePointRate
) {
}
