package com.nhnacademy.bookstorefront.role.dto.response;

import java.util.List;

public record GetUserRoleResponse(
	Long userId,
	List<Long> roleIds,
	List<String> roleNames
) {
}
