package com.nhnacademy.bookstorefront.auth.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.dto.response.LoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.SignUpResponse;
import com.nhnacademy.bookstorefront.auth.service.AuthService;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	// welcome 쿠폰 발행 service
	private final UserAndCouponService userAndCouponService;

	@GetMapping("/sign-up")
	public String signUp() {
		return "auth/sign-up";
	}

	@PostMapping("/sign-up")
	public String signUpProcess(@ModelAttribute SignUpRequest signUpRequest) {
		SignUpResponse signUpResponse;
		try {
			signUpResponse = authService.signUp(signUpRequest).getBody();
		} catch (Exception e) {
			if (e.getMessage().contains("409")) {
				return "redirect:/auth/sign-up?error=" + URLEncoder.encode("해당 이메일은 이미 존재하는 이메일입니다.",
					StandardCharsets.UTF_8);
			}

			return "redirect:/auth/sign-up?error=" + URLEncoder.encode("Error signing up: " + e.getMessage(),
				StandardCharsets.UTF_8);
		}

		// welcome 쿠폰 발행 service method
		if (Objects.nonNull(signUpResponse)) {

			userAndCouponService.createWelcomeCoupon(signUpResponse.id());
		}
		return "redirect:/auth/login";
	}

	/**
	 * 해당 이메일이 존재하는지 확인, 없으면 인증번호 전송
	 * @param email 이메일
	 * @return 해당 이메일이 존재하면 409, 존재하지 않으면 200
	 */
	@PostMapping("/send-email/sign-up")
	public ResponseEntity<Void> sendEmailSignUp(@RequestParam String email) {
		return authService.sendEmailSignUp(email);
	}

	@GetMapping("/check-email/sign-up")
	public ResponseEntity<Void> checkEmailSignUp(@RequestParam String email, @RequestParam String certifyCode) {
		ResponseEntity<Void> response = authService.checkEmailSignUp(email, certifyCode);
		return response;
	}

	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}

	@PostMapping("/login")
	public String loginProcess(@ModelAttribute LoginRequest loginRequest, HttpServletResponse response) {
		LoginResponse loginResponse = authService.login(loginRequest).getBody();

		String accessToken;
		String refreshToken;
		LocalDateTime lastLoginAt;

		if (Objects.isNull(loginResponse)) {
			return "redirect:/auth/login?error=" + URLEncoder.encode("로그인 실패", StandardCharsets.UTF_8);
		}

		accessToken = loginResponse.accessToken();
		refreshToken = loginResponse.refreshToken();
		lastLoginAt = loginResponse.lastLoginAt();

		if (accessToken != null) {
			response.addCookie(createCookie("Authorization", accessToken));
		}

		if (refreshToken != null) {
			response.addCookie(createCookie("Refresh-Token", refreshToken));
		}

		authService.updateLastLoginAt(accessToken, refreshToken, lastLoginAt);

		return "redirect:/";
	}

	@GetMapping("/store-token")
	public String storeToken(@RequestParam String accessToken, Model model) {
		model.addAttribute("accessToken", accessToken);
		return "auth/store-token";
	}

	@ResponseBody
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
		authService.logout();
		revokeToken(response, "Authorization");
		revokeToken(response, "Refresh-Token");
		return ResponseEntity.ok().build();
	}

	@GetMapping("/has-tokens")
	public ResponseEntity<Boolean> hasTokensInCookie(HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(authService.hasTokensInCookie(request));
	}

	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, URLEncoder.encode(value, StandardCharsets.UTF_8));
		// cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		return cookie;
	}

	private void revokeToken(HttpServletResponse response, String cookieName) {
		Cookie revokedTokenCookie = new Cookie(cookieName, "");
		revokedTokenCookie.setHttpOnly(true);
		revokedTokenCookie.setMaxAge(0);
		revokedTokenCookie.setPath("/");
		response.addCookie(revokedTokenCookie);
	}
}
