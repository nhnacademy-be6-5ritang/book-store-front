package com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.bookstorefront.coupon.domain.dto.response.CouponResponseDTO;

public record UserAndCouponResponseDTO(
	Long id,
	CouponResponseDTO coupon,
	String userId,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime usedDate,
	Boolean isUsed
) {}