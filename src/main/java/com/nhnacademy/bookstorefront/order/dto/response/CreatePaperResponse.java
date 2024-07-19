package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;

public record CreatePaperResponse(
	Long paperTypeId,
	String paperName,
	String paperContent,
	BigDecimal paperPrice
) {
}
