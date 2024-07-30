package com.nhnacademy.bookstorefront.role.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.role.dto.response.GetRoleResponse;
import com.nhnacademy.bookstorefront.role.dto.response.GetUserRoleResponse;
import com.nhnacademy.bookstorefront.role.feignclient.RoleClient;

import lombok.RequiredArgsConstructor;

/**
 * @author 김태환
 * 역할 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleClient roleClient;

	/**
	 * 시스템에 정의된 모든 역할을 조회합니다.
	 *
	 * @return 모든 역할의 목록을 포함하는 {@link List} 객체. 각 항목은 {@link GetRoleResponse} DTO
	 */
	public List<GetRoleResponse> getRoles() {
		return roleClient.getRoles().getBody();
	}

	/**
	 * 특정 사용자에 대한 역할 정보를 조회합니다.
	 *
	 * @param userId 조회할 사용자의 ID
	 * @return 특정 사용자의 역할 정보를 포함하는 {@link GetUserRoleResponse} DTO
	 */
	public GetUserRoleResponse getUserRoles(Long userId) {
		return roleClient.getUserRoles(userId).getBody();
	}
}
