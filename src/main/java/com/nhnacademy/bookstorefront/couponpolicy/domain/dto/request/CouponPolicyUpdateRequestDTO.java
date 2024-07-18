package com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record CouponPolicyUpdateRequestDTO(@NotNull BigDecimal minOrderPrice, BigDecimal salePrice, BigDecimal saleRate,
										   BigDecimal maxSalePrice, @NonNull Boolean isUsed) {
}
