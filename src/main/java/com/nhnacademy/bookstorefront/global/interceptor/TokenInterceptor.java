package com.nhnacademy.bookstorefront.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		// 응답 헤더에서 새로운 토큰을 가져옴
		String newAccessToken = response.getHeader("New-Authorization");
		String newRefreshToken = response.getHeader("New-Refresh-Token");

		System.out.println("New Access Token: " + newAccessToken);
		System.out.println("New Refresh Token: " + newRefreshToken);

		if (newAccessToken != null) {
			Cookie accessTokenCookie = new Cookie("Authorization", newAccessToken);
			accessTokenCookie.setPath("/");
			accessTokenCookie.setHttpOnly(true);
			accessTokenCookie.setMaxAge(60 * 60); // 예: 1시간

			response.addCookie(accessTokenCookie);
		}

		if (newRefreshToken != null) {
			Cookie refreshTokenCookie = new Cookie("Refresh-Token", newRefreshToken);
			refreshTokenCookie.setPath("/");
			refreshTokenCookie.setHttpOnly(true);
			refreshTokenCookie.setMaxAge(60 * 60 * 24 * 30); // 예: 30일

			response.addCookie(refreshTokenCookie);
		}
	}
}
