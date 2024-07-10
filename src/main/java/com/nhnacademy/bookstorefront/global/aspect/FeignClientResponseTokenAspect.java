package com.nhnacademy.bookstorefront.global.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class FeignClientResponseTokenAspect {

	@AfterReturning(pointcut = "execution(* com.nhnacademy.bookstorefront..feignclient..*(..))", returning = "responseEntity")
	public void afterReturning(ResponseEntity<?> responseEntity) {
		if (responseEntity != null) {
			String newAccessToken = responseEntity.getHeaders().getFirst("new-authorization");
			String newRefreshToken = responseEntity.getHeaders().getFirst("new-refresh-token");

			if (newAccessToken != null) {
				Cookie accessTokenCookie = new Cookie("Authorization", newAccessToken);
				accessTokenCookie.setHttpOnly(true);
				accessTokenCookie.setPath("/");
				getHttpServletResponse().addCookie(accessTokenCookie);
			}

			if (newRefreshToken != null) {
				Cookie refreshTokenCookie = new Cookie("Refresh-Token", newRefreshToken);
				refreshTokenCookie.setHttpOnly(true);
				refreshTokenCookie.setPath("/");
				getHttpServletResponse().addCookie(refreshTokenCookie);
			}
		}
	}

	private HttpServletResponse getHttpServletResponse() {
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		return attr.getResponse();
	}
}
