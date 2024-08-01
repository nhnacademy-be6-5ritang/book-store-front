package com.nhnacademy.bookstorefront.auth.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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

/**
 * @author 김태환
 * 인증 관련 요청을 처리하는 컨트롤러입니다.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	// welcome 쿠폰 발행 service
	private final UserAndCouponService userAndCouponService;

	/**
	 * 사용자 등록 페이지를 반환합니다.
	 *
	 * @return 사용자 등록 페이지의 뷰 이름
	 */
	@GetMapping("/sign-up")
	public String signUp() {
		return "auth/sign-up";
	}

	/**
	 * 사용자 등록 요청을 처리합니다.
	 *
	 * @param signUpRequest 사용자 등록 요청 정보를 담고 있는 {@link SignUpRequest} 객체
	 * @return 사용자 등록 성공 시 로그인 페이지로 리다이렉트
	 */
	@PostMapping("/sign-up")
	public String signUpProcess(@ModelAttribute SignUpRequest signUpRequest) {
		LocalDate birth = signUpRequest.getBirthDate();
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
	 * 주어진 이메일이 존재하는지 확인하고, 이메일이 존재하지 않으면 인증번호를 전송합니다.
	 *
	 * @param email 인증을 요청할 이메일 주소
	 * @return 이메일이 존재하면 409 상태 코드, 존재하지 않으면 200 상태 코드
	 */
	@PostMapping("/send-email/sign-up")
	public ResponseEntity<Void> sendEmailSignUp(@RequestParam String email) {
		return authService.sendEmailSignUp(email);
	}

	/**
	 * 이메일 인증을 처리합니다.
	 *
	 * @param email 인증을 받을 이메일 주소
	 * @param certifyCode 인증 코드
	 * @return 인증 성공 여부를 담고 있는 {@link ResponseEntity}
	 */
	@GetMapping("/check-email/sign-up")
	public ResponseEntity<Void> checkEmailSignUp(@RequestParam String email, @RequestParam String certifyCode) {
		ResponseEntity<Void> response = authService.checkEmailSignUp(email, certifyCode);
		return response;
	}

	/**
	 * 로그인 페이지를 반환합니다.
	 *
	 * @return 로그인 페이지의 뷰 이름
	 */
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}

	/**
	 * 로그인 요청을 처리합니다.
	 *
	 * @param loginRequest 로그인 요청 정보를 담고 있는 {@link LoginRequest} 객체
	 * @param response HTTP 응답 객체
	 * @return 로그인 성공 시 홈 페이지로 리다이렉트
	 */
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

	/**
	 * 클라이언트가 저장한 액세스 토큰을 모델에 추가하여 반환합니다.
	 *
	 * @param accessToken 클라이언트가 저장한 액세스 토큰
	 * @param model 스프링 MVC 모델 객체
	 * @return 액세스 토큰 저장 페이지의 뷰 이름
	 */
	@GetMapping("/store-token")
	public String storeToken(@RequestParam String accessToken, Model model) {
		model.addAttribute("accessToken", accessToken);
		return "auth/store-token";
	}

	/**
	 * 로그아웃 요청을 처리합니다.
	 *
	 * @param response HTTP 응답 객체
	 * @return 로그아웃 성공 시 HTTP 200 상태 코드
	 */
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
		authService.logout();
		revokeToken(response, "Authorization");
		revokeToken(response, "Refresh-Token");
		return ResponseEntity.ok().build();
	}

	/**
	 * 클라이언트의 쿠키에 저장된 액세스 토큰 및 리프레시 토큰이 존재하는지 확인합니다.
	 *
	 * @param request HTTP 요청 객체
	 * @return 쿠키에 토큰이 존재하면 true, 그렇지 않으면 false를 담고 있는 {@link ResponseEntity}
	 */
	@GetMapping("/has-tokens")
	public ResponseEntity<Boolean> hasTokensInCookie(HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(authService.hasTokensInCookie(request));
	}

	/**
	 * 주어진 키와 값을 사용하여 쿠키를 생성합니다.
	 *
	 * @param key 쿠키의 이름
	 * @param value 쿠키의 값
	 * @return 생성된 {@link Cookie} 객체
	 */
	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, URLEncoder.encode(value, StandardCharsets.UTF_8));
		// cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		return cookie;
	}

	/**
	 * 주어진 쿠키 이름을 사용하여 토큰을 무효화합니다.
	 *
	 * @param response HTTP 응답 객체
	 * @param cookieName 무효화할 쿠키의 이름
	 */
	private void revokeToken(HttpServletResponse response, String cookieName) {
		Cookie revokedTokenCookie = new Cookie(cookieName, "");
		revokedTokenCookie.setHttpOnly(true);
		revokedTokenCookie.setMaxAge(0);
		revokedTokenCookie.setPath("/");
		response.addCookie(revokedTokenCookie);
	}
}
