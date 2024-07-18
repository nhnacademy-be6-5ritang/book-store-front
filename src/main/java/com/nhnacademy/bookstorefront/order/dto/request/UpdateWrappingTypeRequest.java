package com.nhnacademy.bookstorefront.order.dto.request;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record UpdateWrappingTypeRequest(
	String paperName,
	String paperContent,
	BigDecimal paperPrice
) {
}
