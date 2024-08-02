package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 공통
 * 인덱스 페이지 및 사용자 관련 페이지를 처리하는 컨트롤러 클래스입니다.
 */
@Controller
public class IndexController {

	/**
	 * 인덱스 페이지 처리 메서드입니다. 기본 경로("/")로 접근할 경우 `/api/books/main`으로 리다이렉트합니다.
	 *
	 * @return 인덱스 페이지 리다이렉션 경로
	 */
	@GetMapping("/")
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

	@GetMapping("/template")
	public String showTemplate() {
		return "menu-template";
	}
}
