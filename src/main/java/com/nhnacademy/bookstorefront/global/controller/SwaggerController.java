package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.bookstorefront.global.feignclient.SwaggerApiClient;

import lombok.RequiredArgsConstructor;

/**
 * @author 이기훈
 * SwaggerController는 Swagger API 문서를 프론트엔드에서 볼 수 있도록 데이터를 제공하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
public class SwaggerController {
	private final SwaggerApiClient swaggerApiClient;

	/**
	 * 쿠폰 API 문서를 가져와서 모델에 추가합니다.
	 *
	 * @param model Spring MVC 모델
	 * @return 쿠폰 API 문서를 표시하는 뷰 이름
	 */
	@GetMapping("/coupons/api")
	public String getCouponApi(Model model) {
		try {
			ResponseEntity<String> response = swaggerApiClient.getSwaggerJson();
			String swaggerJson = response.getBody() != null ? response.getBody() : "{}";
			model.addAttribute("swaggerJson", swaggerJson);
		} catch (Exception e) {
			model.addAttribute("swaggerJson", "{}");
		}
		return "api/coupon-api";
	}

	/**
	 * 백엔드 API 문서를 가져와서 모델에 추가합니다.
	 *
	 * @param model Spring MVC 모델
	 * @return 백엔드 API 문서를 표시하는 뷰 이름
	 */
	@GetMapping("/api")
	public String getBackApi(Model model) {
		try {
			ResponseEntity<String> response = swaggerApiClient.getBackSwaggerJson();
			String swaggerJson = response.getBody() != null ? response.getBody() : "{}";
			model.addAttribute("swaggerJson", swaggerJson);
		} catch (Exception e) {
			model.addAttribute("swaggerJson", "{}");
		}
		return "api/back-api";
	}

	/**
	 * 인증 API 문서를 가져와서 모델에 추가합니다.
	 *
	 * @param model Spring MVC 모델
	 * @return 인증 API 문서를 표시하는 뷰 이름
	 */
	@GetMapping("/auth")
	public String getAuthApi(Model model) {
		try {
			ResponseEntity<String> response = swaggerApiClient.getAuthSwaggerJson();
			String swaggerJson = response.getBody() != null ? response.getBody() : "{}";
			model.addAttribute("swaggerJson", swaggerJson);
		} catch (Exception e) {
			model.addAttribute("swaggerJson", "{}");
		}
		return "api/auth-api";
	}
}
