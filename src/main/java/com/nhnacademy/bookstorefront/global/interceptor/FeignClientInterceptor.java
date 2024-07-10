package com.nhnacademy.bookstorefront.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FeignClientInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (Cookie cookie : cookies) {
					log.info("[Interceptor] setting cookie to header: {}={}", cookie.getName(), cookie.getValue());
					if ("Authorization".equals(cookie.getName())) {
						template.header("Authorization", cookie.getValue());
					} else if ("Refresh-Token".equals(cookie.getName())) {
						template.header("Refresh-Token", cookie.getValue());
					}
				}
			}
		}
	}
}
