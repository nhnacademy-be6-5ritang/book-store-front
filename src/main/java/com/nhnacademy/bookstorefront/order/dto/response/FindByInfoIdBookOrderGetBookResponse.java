package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;

public record FindByInfoIdBookOrderGetBookResponse(
	String bookTitle,
	BigDecimal bookPrice,
	String bookDescription
) {

}
