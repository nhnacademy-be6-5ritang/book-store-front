package com.nhnacademy.bookstorefront.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			HttpSession session = request.getSession();

			String accessToken = (String)session.getAttribute("accessToken");
			if (accessToken != null) {
				template.header("Authorization", accessToken);
			}

			String refreshToken = (String)session.getAttribute("refreshToken");
			if (refreshToken != null) {
				template.header("Refresh-Token", refreshToken);
			}
		}
	}
}
