package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;

public record GetWrappingResponse(
	Long wrappingId,
	String name,
	BigDecimal price,
	Integer quantity
) {

}
