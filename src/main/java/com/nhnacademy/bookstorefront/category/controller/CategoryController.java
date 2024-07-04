package com.nhnacademy.bookstorefront.category.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.service.impl.CategoryServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 관리 웹 페이지 컨트롤러입니다.
 * 이 컨트롤러는 카테고리 정보를 생성, 조회, 수정, 삭제하는 기능을 제공합니다.
 * 또한 웹 페이지에서 사용할 뷰를 반환합니다.
 *
 * <p>카테고리 목록 조회 시 페이징 처리를 지원합니다.
 *
 * @author [작성자]
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
	private final CategoryServiceImpl categoryService;

	/**
	 * 새로운 카테고리 생성 폼을 반환합니다.
	 *
	 * @param model 모델 객체
	 * @return 카테고리 생성 폼 뷰 이름
	 */
	@GetMapping("/create")
	public String createCategoryForm(Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		return "category/create-category";
	}

	/**
	 * 주어진 카테고리 ID에 해당하는 카테고리 정보를 수정하는 폼을 반환합니다.
	 *
	 * @param categoryId 카테고리 ID
	 * @param model      모델 객체
	 * @return 카테고리 수정 폼 뷰 이름
	 */
	@GetMapping("/update/{categoryId}")
	public String updateCategoryForm(@PathVariable Long categoryId, Model model) {
		model.addAttribute("category", categoryService.getCategory(categoryId));
		model.addAttribute("categories", categoryService.getCategories());
		return "category/update-category";
	}

	/**
	 * 모든 카테고리 정보를 조회합니다.
	 *
	 * @param model 모델 객체
	 * @return 카테고리 리스트 뷰 이름
	 */
	@GetMapping
	public String getCategories(Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		return "category/list-category";
	}

	/**
	 * 카테고리 정보를 페이지로 조회합니다.
	 *
	 * @param pageable 페이지 정보
	 * @param model    모델 객체
	 * @return 카테고리 리스트 뷰 이름
	 */
	@GetMapping("/page")
	public String getCategories(@PageableDefault(page = 1) Pageable pageable, Model model) {
		Page<GetCategoryResponse> categories = categoryService.getCategories(pageable);
		model.addAttribute("categories", categories);
		model.addAttribute("objects", categories); // 공통 객체 이름
		model.addAttribute("baseUrl", "/api/categories/page"); // 페이징 URL

		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), categories.getTotalPages());

		model.addAttribute("pageable", pageable);
		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "category/list-category";
	}

	/**
	 * 새로운 카테고리 정보를 생성합니다.
	 *
	 * @param request 생성할 카테고리 정보 DTO
	 * @return 카테고리 리스트 페이지로 리다이렉트
	 */
	@PostMapping
	public String createCategory(@ModelAttribute CreateCategoryRequest request) {
		categoryService.createCategory(request);
		return "redirect:/api/categories/page";
	}

	/**
	 * 주어진 카테고리 ID에 해당하는 카테고리 정보를 수정합니다.
	 *
	 * @param categoryId 카테고리 ID
	 * @param request    수정할 카테고리 정보 DTO
	 * @return 카테고리 리스트 페이지로 리다이렉트
	 */
	@PutMapping("/{categoryId}")
	public String updateCategory(@PathVariable Long categoryId, @ModelAttribute UpdateCategoryRequest request) {
		categoryService.updateCategory(categoryId, request);
		return "redirect:/api/categories/page";
	}

	/**
	 * 주어진 카테고리 ID에 해당하는 카테고리 정보를 삭제합니다.
	 *
	 * @param categoryId 삭제할 카테고리 ID
	 * @return 카테고리 리스트 페이지로 리다이렉트
	 */
	@DeleteMapping("/{categoryId}")
	public String deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return "redirect:/api/categories/page";
	}
}
