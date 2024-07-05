package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nhnacademy.bookstorefront.global.config.CacheConfig;

import lombok.RequiredArgsConstructor;

/**
 * 전역 데이터를 처리하는 컨트롤러 어드바이스 클래스입니다.
 *
 * @version 1.0
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalDataControllerAdvice {
	private final CacheConfig cacheConfig;

	@ModelAttribute
	public void categories(Model model) {
		model.addAttribute("categoriesCache", cacheConfig.getCachedCategories());
	}
}
