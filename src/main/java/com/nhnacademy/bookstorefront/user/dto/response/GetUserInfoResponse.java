package com.nhnacademy.bookstorefront.user.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetUserInfoResponse(
	Long userId,
	String name,
	String email,
	LocalDateTime createdAt,
	List<String> roles,
	String userGradeName,
	String userStatusName
) {
}
