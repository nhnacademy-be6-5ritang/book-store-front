package com.nhnacademy.bookstorefront.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.product.service.ProductService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌
 * 메인페이지 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
	private final ProductService productService;

	/**
	 * 메인 페이지의 상품을 조회합니다.
	 *
	 * @param model 모델 객체
	 * @return 메인 페이지 뷰 이름
	 */
	@GetMapping
	public String mainPage(Model model) {
		model.addAttribute("bestSellerProducts", productService.getBestSellerBooks());
		model.addAttribute("likesProducts", productService.getLikesBooks());
		model.addAttribute("newestProducts", productService.getNewestBooks());
		return "index";
	}

}
