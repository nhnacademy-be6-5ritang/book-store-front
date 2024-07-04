package com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request;

import java.time.LocalDateTime;

public record CouponTemplateCreateRequestDTO(
	Long couponPolicyId,


	LocalDateTime expiredDate,

	LocalDateTime issueDate
) {}