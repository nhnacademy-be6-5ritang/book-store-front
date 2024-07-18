package com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CouponPolicyCreateRequestDTO(@NotNull BigDecimal minOrderPrice,
										   BigDecimal salePrice,
										   BigDecimal saleRate,
										   BigDecimal maxSalePrice,
										   @NotBlank String type, Long bookId, String bookTitle, Long categoryId, String categoryName) {
}