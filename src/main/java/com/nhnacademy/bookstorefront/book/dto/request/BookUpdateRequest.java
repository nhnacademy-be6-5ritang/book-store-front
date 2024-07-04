package com.nhnacademy.bookstorefront.book.dto.request;

import java.math.BigDecimal;

/**
 * 책 수정 요청 DTO
 *
 * @version 1.0
 */
public record BookUpdateRequest(
	String statusName,
	String description,
	String index,
	int quantity,
	BigDecimal price,
	BigDecimal salePrice) {
}
