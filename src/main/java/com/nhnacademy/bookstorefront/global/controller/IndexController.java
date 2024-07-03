package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping
	public String indexPage() {
		return "redirect:/api/books/main";
	}

	@GetMapping("/api/users/admin")
	public String adminPage() {
		return "admin/admin-account";
	}

	@GetMapping("/api/users/me")
	public String userPage() {
		return "user/user-account";
	}
}
