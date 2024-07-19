package com.nhnacademy.bookstorefront.order.dto.request;

import java.math.BigDecimal;

public record CreateWrappingTypeRequest(
	String paperName,
	String paperContent,
	BigDecimal paperPrice
) {

}
