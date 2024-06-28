package com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request;

import java.math.BigDecimal;

public record CouponPolicyCreateRequestDTO(BigDecimal minOrderPrice, BigDecimal salePrice, BigDecimal saleRate,
										   BigDecimal maxSalePrice, String type, Long bookId, Long categoryId) {
}