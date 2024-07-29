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

@Controller
@RequiredArgsConstructor
public class AdminUserController {
	private final UserService userService;
	private final RoleService roleService;

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

	@PostMapping("/admin/users/role")
	public ModelAndView addRole(@ModelAttribute UpdateUserRoleRequest updateUserRoleRequest) {
		userService.updateUserRole(updateUserRoleRequest);
		return new ModelAndView("redirect:/admin/users");
	}
}
