package com.nhnacademy.bookstorefront.coupontemplate.domain.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponTemplateResponseDTO(
	Long id,
	Long couponPolicyId,
	BigDecimal minOrderPrice,
	BigDecimal salePrice,
	BigDecimal saleRate,
	BigDecimal maxSalePrice,
	String type,
	Boolean isUsed,
	Long bookId,
	Long categoryId,
	LocalDateTime expiredDate,
	LocalDateTime issueDate
) {

}
