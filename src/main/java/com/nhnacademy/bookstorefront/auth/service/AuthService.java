package com.nhnacademy.bookstorefront.auth.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.auth.dto.request.LoginRequest;
import com.nhnacademy.bookstorefront.auth.dto.request.SignUpRequest;
import com.nhnacademy.bookstorefront.auth.dto.response.LoginResponse;
import com.nhnacademy.bookstorefront.auth.dto.response.SignUpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 김태환
 * 인증과 관련된 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 */
public interface AuthService {
	/**
	 * 사용자의 로그인 요청을 처리합니다.
	 *
	 * @param loginRequest 로그인 요청 정보
	 * @return {@link LoginResponse}가 포함된 {@link ResponseEntity}
	 */
	ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

	/**
	 * 사용자의 로그아웃 요청을 처리합니다.
	 */
	void logout();

	/**
	 * 사용자의 회원가입 요청을 처리합니다.
	 *
	 * @param signUpRequest 회원가입 요청 정보
	 * @return {@link SignUpResponse}가 포함된 {@link ResponseEntity}
	 */
	ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest);

	/**
	 * 회원가입을 위해 인증 이메일을 전송합니다.
	 *
	 * @param email 인증 이메일
	 * @return 처리 결과를 나타내는 {@link ResponseEntity}
	 */
	ResponseEntity<Void> sendEmailSignUp(String email);

	/**
	 * HTTP 요청에 쿠키로 토큰이 포함되어 있는지 확인합니다.
	 *
	 * @param request HTTP 요청 객체
	 * @return 쿠키로 토큰이 포함되어 있으면 {@code true}, 아니면 {@code false}
	 */
	boolean hasTokensInCookie(HttpServletRequest request);

	/**
	 * 사용자의 마지막 로그인 시간을 업데이트합니다.
	 *
	 * @param accessToken 액세스 토큰
	 * @param refreshToken 리프레시 토큰
	 * @param lastLoginAt 마지막 로그인 시간
	 */
	void updateLastLoginAt(String accessToken, String refreshToken, LocalDateTime lastLoginAt);

	/**
	 * 회원가입을 위한 이메일 인증 코드를 확인합니다.
	 *
	 * @param email 인증할 이메일
	 * @param certifyCode 인증 코드
	 * @return 처리 결과를 나타내는 {@link ResponseEntity}
	 */
	ResponseEntity<Void> checkEmailSignUp(String email, String certifyCode);

	/**
	 * PAYCO 사용자를 위한 토큰을 발급합니다.
	 *
	 * @param memberNumber PAYCO 사용자 번호
	 * @param response HTTP 응답 객체
	 */
	void getTokensForPaycoUser(String memberNumber, HttpServletResponse response);
}
