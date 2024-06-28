package com.nhnacademy.bookstorefront.order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateBookOrderResponse(
	String orderInfoId,
	LocalDateTime orderDate,
	BigDecimal totalPrice,
	String bookTitle,
	BigDecimal bookPrice,
	Integer quantity
) {

}
