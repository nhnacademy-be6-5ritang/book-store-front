package com.nhnacademy.bookstorefront.global.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nhnacademy.bookstorefront.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalDataControllerAdvice {
	private final CategoryService categoryService;

	@ModelAttribute
	public void categories(Model model) {
		// 여기서 카테고리 데이터를 로드하고 반환
		model.addAttribute("categoriesDisplay", categoryService.getCategories());
	}
}
