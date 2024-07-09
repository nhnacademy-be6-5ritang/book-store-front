package com.nhnacademy.bookstorefront.auth.dto.response;

import java.time.LocalDate;

public record SignUpResponse(
	Long id,
	String name,
	String email,
	LocalDate birth,
	String contact,
	String userStatus,
	String userGrade
) {
}
