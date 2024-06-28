package com.nhnacademy.bookstorefront.coupontemplate.domain.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CouponTemplateCreateRequestDTO(
	Long couponPolicyId,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime expiredDate,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime issueDate
) {}