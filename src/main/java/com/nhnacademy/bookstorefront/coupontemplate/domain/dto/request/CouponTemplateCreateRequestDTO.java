package com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CouponTemplateCreateRequestDTO(
	@NotNull
	Long couponPolicyId,

	@NotNull
	LocalDateTime expiredDate,

	@NotNull
	LocalDateTime issueDate,

	@NotNull
	@Positive
	Long quantity
) {}

