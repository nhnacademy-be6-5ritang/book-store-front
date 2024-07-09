package com.nhnacademy.bookstorefront.user.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record GetMyUserInfoResponse(
	String name,
	String email,
	LocalDate birth,
	String contact,
	LocalDateTime createdAt,
	List<String> roles,
	String userGradeName,
	String userStatusName,
	BigDecimal points
) {
}
