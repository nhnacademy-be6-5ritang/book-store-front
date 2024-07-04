package com.nhnacademy.bookstorefront.auth.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}

	@PostMapping("/login")
	public String loginProcess(@ModelAttribute LoginRequest loginRequest, HttpServletResponse response,
		RedirectAttributes redirectAttributes) {
		ResponseEntity<Void> loginResponse = authService.login(loginRequest);

		String accessToken = loginResponse.getHeaders().getFirst("Authorization");

		String refreshToken = null;
		if (loginResponse.getHeaders().containsKey("Set-Cookie")) {
			for (String cookie : Objects.requireNonNull(loginResponse.getHeaders().get("Set-Cookie"))) {
				if (cookie.startsWith("Refresh-Token")) {
					refreshToken = cookie.split(";")[0].split("=")[1];
					break;
				}
			}
		}

		if (refreshToken != null) {
			Cookie refreshTokenCookie = new Cookie("Refresh-Token", refreshToken);
			refreshTokenCookie.setHttpOnly(true);
			refreshTokenCookie.setMaxAge(24 * 60 * 60);
			refreshTokenCookie.setPath("/");
			response.addCookie(refreshTokenCookie);
		}

		redirectAttributes.addAttribute("accessToken", accessToken);
		return "redirect:/auth/store-token";
	}

	@GetMapping("/store-token")
	public String storeToken(@RequestParam String accessToken, Model model) {
		model.addAttribute("accessToken", accessToken);
		return "auth/store-token";
	}

	@PostMapping("/logout")
	public String logout(@RequestParam String accessToken, HttpServletResponse response) {
		authService.logout(accessToken);

		Cookie revokedRefreshTokenCookie = new Cookie("Refresh-Token", "");
		revokedRefreshTokenCookie.setHttpOnly(true);
		revokedRefreshTokenCookie.setMaxAge(0);
		revokedRefreshTokenCookie.setPath("/");
		response.addCookie(revokedRefreshTokenCookie);

		return "redirect:/auth/remove-token";
	}

	@GetMapping("/remove-token")
	public String removeToken() {
		return "auth/remove-token";
	}
}
