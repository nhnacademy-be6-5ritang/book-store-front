package com.nhnacademy.bookstorefront.user.dto.response;

import java.time.LocalDate;

public record UpdateUserInfoResponse(
	String name,
	String email,
	LocalDate birth,
	String contact
) {

}
