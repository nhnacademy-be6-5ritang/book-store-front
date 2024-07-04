package com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request;

import java.time.LocalDateTime;

public record UserAndCouponRequestUpdateDTO(
	LocalDateTime usedDate,
	Boolean isUsed
) {}