package com.nhnacademy.bookstorefront.user.dto.request;

import java.util.List;

import lombok.Builder;

@Builder
public record UpdateUserRoleRequest(
	Long userId,
	List<String> roleName
) {
}
