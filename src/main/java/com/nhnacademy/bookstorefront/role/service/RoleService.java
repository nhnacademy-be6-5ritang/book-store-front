package com.nhnacademy.bookstorefront.role.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.role.dto.response.GetRoleResponse;
import com.nhnacademy.bookstorefront.role.dto.response.GetUserRoleResponse;
import com.nhnacademy.bookstorefront.role.feignclient.RoleClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleClient roleClient;

	public List<GetRoleResponse> getRoles() {
		return roleClient.getRoles().getBody();
	}

	public GetUserRoleResponse getUserRoles(Long userId) {
		return roleClient.getUserRoles(userId).getBody();
	}
}
