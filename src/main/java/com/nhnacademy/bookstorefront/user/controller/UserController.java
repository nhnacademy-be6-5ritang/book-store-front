package com.nhnacademy.bookstorefront.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.bookstorefront.user.dto.response.GetMyUserInfoResponse;
import com.nhnacademy.bookstorefront.user.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@GetMapping("/my-page")
	public String MyUserInfoPage(Model model) {
		ResponseEntity<GetMyUserInfoResponse> getMyUserInfoResponse = userService.getMyUserInfo();
		model.addAttribute("myUserInfo", getMyUserInfoResponse.getBody());
		return "user/my-page";
	}

	@ResponseBody
	@PatchMapping("/dormant")
	public ResponseEntity<Void> dormantUser(HttpServletResponse response) {
		revokeToken(response, "Authorization");
		revokeToken(response, "Refresh-Token");
		return userService.dormantUser(response);
	}

	private void revokeToken(HttpServletResponse response, String cookieName) {
		Cookie revokedTokenCookie = new Cookie(cookieName, "");
		revokedTokenCookie.setHttpOnly(true);
		revokedTokenCookie.setMaxAge(0);
		revokedTokenCookie.setPath("/");
		response.addCookie(revokedTokenCookie);
	}
}
