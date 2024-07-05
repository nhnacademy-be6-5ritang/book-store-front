package com.nhnacademy.bookstorefront.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@GetMapping("/my-page")
	public String signUp() {
		return "user/my-page";
	}

	@PatchMapping("/dormant")
	public ResponseEntity<Void> dormantUser() {
		return userService.dormantUser();
	}
}
