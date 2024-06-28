package com.nhnacademy.bookstorefront.userandcoupon.domain.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record UserAndCouponRequestUpdateDTO(
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime usedDate,
	Boolean isUsed
) {}