package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;

public record GetPaperResponse(
	Long paperId,
	String paperName,
	String paperContent,
	BigDecimal paperPrice
) {

}
