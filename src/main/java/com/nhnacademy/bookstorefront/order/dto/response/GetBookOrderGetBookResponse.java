package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;

public record GetBookOrderGetBookResponse(
	String bookTitle,
	BigDecimal bookPrice,
	String bookDescription,
	boolean bookPackaging
) {
}
