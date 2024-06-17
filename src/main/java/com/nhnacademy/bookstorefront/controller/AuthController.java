package com.nhnacademy.bookstorefront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/login")
public class AuthController {

	@GetMapping
	public String getLoginPage() {
		return "login";
	}
}
