package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;

public record GetBookResponse(
	String bookTitle,
	String bookDeDescription,
	BigDecimal bookPrice,
	Boolean bookPackaging
) {

}
