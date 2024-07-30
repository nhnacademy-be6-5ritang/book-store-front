package com.nhnacademy.bookstorefront.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.cache.service.CacheService;
import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;
import com.nhnacademy.bookstorefront.role.dto.response.GetRoleResponse;
import com.nhnacademy.bookstorefront.role.service.RoleService;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserRoleRequest;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserInfoResponse;
import com.nhnacademy.bookstorefront.user.service.UserService;

@WebMvcTest(AdminUserController.class)
class AdminUserControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private CacheService cacheService;

	@MockBean
	private GlobalDataControllerAdvice globalDataControllerAdvice;

	@MockBean
	private UserService userService;

	@MockBean
	private RoleService roleService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminUserController(userService, roleService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	void testAdminPage() throws Exception {
		mockMvc.perform(get("/admin"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/admin-page"));
	}

	@Test
	void testUserListPage() throws Exception {
		// Arrange
		GetUserInfoResponse userResponse = new GetUserInfoResponse(
			1L, "John Doe", "john@example.com", LocalDateTime.now(), Collections.singletonList("Admin"),
			"Regular", "Active"
		);
		Page<GetUserInfoResponse> userPage = new PageImpl<>(Collections.singletonList(userResponse),
			PageRequest.of(0, 10), 1);

		GetRoleResponse roleResponse = new GetRoleResponse("Admin");
		List<GetRoleResponse> roleList = Collections.singletonList(roleResponse);

		when(userService.getUsers(any(Pageable.class))).thenReturn(ResponseEntity.ok(userPage));
		when(roleService.getRoles()).thenReturn(roleList);

		// Act & Assert
		mockMvc.perform(get("/admin/users")
				.param("page", "0")
				.param("size", "10")
				.param("sort", "userId,asc"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/user-list"))
			.andExpect(model().attributeExists("users"))
			.andExpect(model().attributeExists("roles"));
	}

	@Test
	void testAddRole() throws Exception {
		// Arrange
		UpdateUserRoleRequest request = new UpdateUserRoleRequest(1L, List.of("Admin"));

		// Act & Assert
		mockMvc.perform(post("/admin/users/role")
				.flashAttr("updateUserRoleRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/admin/users"));
	}
}
