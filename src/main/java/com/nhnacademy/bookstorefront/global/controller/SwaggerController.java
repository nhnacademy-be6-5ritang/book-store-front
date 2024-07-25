package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.bookstorefront.global.feignclient.SwaggerApiClient;

@Controller
public class SwaggerController {

	private final SwaggerApiClient swaggerApiClient;

	public SwaggerController(SwaggerApiClient swaggerApiClient) {
		this.swaggerApiClient = swaggerApiClient;
	}

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
}