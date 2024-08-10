package com.nhnacademy.bookstorefront.user.dto.response;

import java.util.List;

public record GetAllUserGradeResponse(
	List<GetUserGradeResponse> userGrades
) {
}
