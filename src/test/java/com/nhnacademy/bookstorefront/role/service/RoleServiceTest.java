package com.nhnacademy.bookstorefront.role.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.role.dto.response.GetRoleResponse;
import com.nhnacademy.bookstorefront.role.dto.response.GetUserRoleResponse;
import com.nhnacademy.bookstorefront.role.feignclient.RoleClient;

class RoleServiceTest {

	@Mock
	private RoleClient roleClient;

	@InjectMocks
	private RoleService roleService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetRoles() {
		// Arrange
		GetRoleResponse roleResponse = new GetRoleResponse("Admin");
		List<GetRoleResponse> roleList = Collections.singletonList(roleResponse);
		ResponseEntity<List<GetRoleResponse>> responseEntity = ResponseEntity.ok(roleList);

		when(roleClient.getRoles()).thenReturn(responseEntity);

		// Act
		List<GetRoleResponse> roles = roleService.getRoles();

		// Assert
		assertThat(roles).isNotNull();
		assertThat(roles).hasSize(1);
		assertThat(roles.get(0).roleName()).isEqualTo("Admin");
	}

	@Test
	void testGetUserRoles() {
		// Arrange
		GetUserRoleResponse userRoleResponse = new GetUserRoleResponse(
			1L,
			List.of(1L, 2L),
			List.of("Admin", "User")
		);
		ResponseEntity<GetUserRoleResponse> responseEntity = ResponseEntity.ok(userRoleResponse);

		when(roleClient.getUserRoles(anyLong())).thenReturn(responseEntity);

		// Act
		GetUserRoleResponse userRoles = roleService.getUserRoles(1L);

		// Assert
		assertThat(userRoles).isNotNull();
		assertThat(userRoles.userId()).isEqualTo(1L);
		assertThat(userRoles.roleIds()).containsExactly(1L, 2L);
		assertThat(userRoles.roleNames()).containsExactly("Admin", "User");
	}
}