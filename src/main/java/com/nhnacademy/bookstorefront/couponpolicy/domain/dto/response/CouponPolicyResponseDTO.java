package com.nhnacademy.bookstorefront.couponpolicy.domain.dto.response;

import java.math.BigDecimal;

public record CouponPolicyResponseDTO(Long couponPolicyId, BigDecimal minOrderPrice, BigDecimal salePrice, BigDecimal saleRate,
									  BigDecimal maxSalePrice, String type) {
}