package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;


public record CreateBookOrderGetBookResponse(
	String bookTitle,
	BigDecimal bookPrice,
	String bookDescription
) {
}
