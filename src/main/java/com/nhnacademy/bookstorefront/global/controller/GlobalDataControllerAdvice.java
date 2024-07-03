package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nhnacademy.bookstorefront.category.service.impl.CategoryServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * 전역 데이터를 처리하는 컨트롤러 어드바이스 클래스입니다.
 *
 * @version 1.0
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalDataControllerAdvice {
	private final CategoryServiceImpl categoryService;

	/**
	 * 모든 요청에 대해 카테고리 데이터를 모델에 추가합니다.
	 *
	 * @param model 모델 객체
	 */
	@ModelAttribute
	public void categories(Model model) {
		// 여기서 카테고리 데이터를 로드하고 반환
		model.addAttribute("categoriesDisplay", categoryService.getCategories());
	}
}
