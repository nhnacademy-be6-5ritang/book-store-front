package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping()
	public String indexPage() {
		return "redirect:/books/main";
	}

	@GetMapping("/admin")
	public String adminPage() {
		return "admin/admin-account";
	}

	@GetMapping("/user")
	public String userPage() {
		return "user/user-account";
	}
}
