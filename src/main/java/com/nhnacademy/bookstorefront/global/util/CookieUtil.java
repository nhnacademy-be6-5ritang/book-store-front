package com.nhnacademy.bookstorefront.global.util;

import java.util.List;

import org.springframework.http.HttpHeaders;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 이경헌
 * 서블릿 환경에서 쿠키를 처리하기 위한 유틸리티 클래스입니다.
 */
public class CookieUtil {
	// 인스턴스화를 방지하기 위한 private 생성자
	private CookieUtil() {
	}

	/**
	 * 주어진 HttpHeaders 에서 Set-Cookie 헤더를 가져와 HttpServletResponse 에 쿠키를 추가합니다.
	 *
	 * @param headers 쿠키가 포함된 HttpHeaders 객체입니다.
	 * @param resp    쿠키를 추가할 HttpServletResponse 객체입니다.
	 */
	public static void responseCookies(HttpHeaders headers, HttpServletResponse resp) {
		List<String> cookies = headers.get(HttpHeaders.SET_COOKIE);

		if (cookies != null) {
			for (String cookieHeader : cookies) {
				Cookie cookie = CookieUtil.createCookieFromHeader(cookieHeader);
				resp.addCookie(cookie);
			}
		}
	}

	/**
	 * Set-Cookie 헤더 문자열을 Cookie 객체로 변환합니다.
	 *
	 * @param cookieHeader Set-Cookie 헤더 문자열입니다.
	 * @return 생성된 Cookie 객체입니다.
	 */
	public static Cookie createCookieFromHeader(String cookieHeader) {
		String[] cookieParts = cookieHeader.split(";");
		String[] nameValue = cookieParts[0].split("=");

		String name = nameValue[0].trim();
		String value = nameValue[1].trim();
		Cookie cookie = new Cookie(name, value);

		for (int i = 1; i < cookieParts.length; i++) {
			String part = cookieParts[i].trim();
			if (part.equalsIgnoreCase("HttpOnly")) {
				cookie.setHttpOnly(true);
			} else if (part.equalsIgnoreCase("Secure")) {
				cookie.setSecure(true);
			} else if (part.toLowerCase().startsWith("max-age")) {
				String[] maxAgeParts = part.split("=");
				int maxAge = Integer.parseInt(maxAgeParts[1].trim());
				cookie.setMaxAge(maxAge);
			} else if (part.toLowerCase().startsWith("path")) {
				String[] pathParts = part.split("=");
				String path = pathParts[1].trim();
				cookie.setPath(path);
			} else if (part.toLowerCase().startsWith("domain")) {
				String[] domainParts = part.split("=");
				String domain = domainParts[1].trim();
				cookie.setDomain(domain);
			}
		}

		return cookie;
	}
}
