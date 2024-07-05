package com.nhnacademy.bookstorefront.auth.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	@GetMapping("/sign-up")
	public String signUp() {
		return "auth/sign-up";
	}

	@PostMapping("/sign-up")
	public String signUpProcess(@ModelAttribute SignUpRequest signUpRequest) {
		try {
			authService.signUp(signUpRequest);
		} catch (Exception e) {
			if (e.getMessage().contains("409")) {
				return "redirect:/auth/sign-up?error=" + URLEncoder.encode("해당 이메일은 이미 존재하는 이메일입니다.",
					StandardCharsets.UTF_8);
			}

			return "redirect:/auth/sign-up?error=" + URLEncoder.encode("Error signing up: " + e.getMessage(),
				StandardCharsets.UTF_8);
		}
		return "redirect:/auth/login";
	}

	/**
	 * 해당 이메일이 존재하는지 확인
	 * @param email 이메일
	 * @return 해당 이메일이 존재하면 true, 존재하지 않으면 false
	 */
	@GetMapping("/check-email")
	public ResponseEntity<Boolean> isEmailExist(@RequestParam String email) {
		return authService.isEmailExist(email);
	}

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

	@ResponseBody
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		log.error("로그아웃 API 시작");
		authService.logout();
		log.error("- 인증서버로 로그아웃 요청");

		Cookie revokedRefreshTokenCookie = new Cookie("Refresh-Token", "");
		revokedRefreshTokenCookie.setHttpOnly(true);
		revokedRefreshTokenCookie.setMaxAge(0);
		revokedRefreshTokenCookie.setPath("/");
		response.addCookie(revokedRefreshTokenCookie);
		log.error("쿠키 제거 완료");
		log.error("메인 페이지로 redirect");
		return ResponseEntity.ok().build();
	}
}
