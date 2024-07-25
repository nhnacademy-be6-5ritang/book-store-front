package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌
 * 전역 데이터를 처리하는 컨트롤러 어드바이스 클래스입니다.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalDataControllerAdvice {
	private final CacheServiceImpl cacheService;

	@ModelAttribute
	public void categories(Model model) {
		model.addAttribute("categoriesCache", cacheService.getCachedCategories());
	}
}
