package com.nhnacademy.bookstorefront.user.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.role.dto.response.GetRoleResponse;
import com.nhnacademy.bookstorefront.role.service.RoleService;
import com.nhnacademy.bookstorefront.user.dto.request.UpdateUserRoleRequest;
import com.nhnacademy.bookstorefront.user.dto.response.GetUserInfoResponse;
import com.nhnacademy.bookstorefront.user.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author 김태환
 * 관리자 관련 사용자 관리 웹 요청을 처리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
public class AdminUserController {
	private final UserService userService;
	private final RoleService roleService;

	/**
	 * 관리자 대시보드 페이지를 반환합니다.
	 *
	 * @return 관리자 페이지의 뷰 이름
	 */
	@GetMapping("/admin")
	public String adminPage() {
		userService.getAdminPage();
		return "admin/admin-page";
	}

	/**
	 * 사용자 목록 페이지를 반환합니다.
	 *
	 * @param pageable 페이지네이션 정보
	 * @param model 모델 객체
	 * @return 사용자 목록 페이지의 뷰 이름
	 */
	@GetMapping("/admin/users")
	public String userListPage(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model) {
		Page<GetUserInfoResponse> users = userService.getUsers(pageable).getBody();
		model.addAttribute("users", users);

		// if (Objects.nonNull(users) && users.hasContent()) {
		// 	for (GetUserInfoResponse user : users) {
		// 		List<String> roles = roleService.getUserRoles(user.userId()).roleNames();
		// 		model.addAttribute("roles_" + user.userId(), roles);
		// 	}
		// }

		List<GetRoleResponse> roles = roleService.getRoles();
		model.addAttribute("roles", roles);
		PagingModel.pagingProcessing(pageable, model, users, "/admin/users", 5);
		return "user/user-list";
	}

	/**
	 * 사용자의 역할을 추가 또는 수정합니다.
	 *
	 * @param updateUserRoleRequest 역할 업데이트 요청 정보가 포함된 {@link UpdateUserRoleRequest} DTO
	 * @return 사용자 목록 페이지로 리다이렉트하는 {@link ModelAndView} 객체
	 */
	@PostMapping("/admin/users/role")
	public ModelAndView addRole(@ModelAttribute UpdateUserRoleRequest updateUserRoleRequest) {
		userService.updateUserRole(updateUserRoleRequest);
		return new ModelAndView("redirect:/admin/users");
	}

}
