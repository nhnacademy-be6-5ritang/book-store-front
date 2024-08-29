package com.nhnacademy.bookstorefront.category.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.response.CategorySearchResult;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.bookstorefront.global.util.PagingModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author 김기욱, 이경헌
 * 카테고리 관리 웹 페이지 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
	private final CategoryServiceImpl categoryService;
	private static final String REDIRECT_URL = "redirect:/api/categories/page";

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
	public String getCategories(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model) {
		Page<GetCategoryResponse> categories = categoryService.getCategories(pageable);
		model.addAttribute("categories", categories);
		PagingModel.pagingProcessing(pageable, model, categories, "/api/categories/page", 5);

		return "category/list-category";
	}

	/**
	 * 새로운 카테고리 정보를 생성합니다.
	 *
	 * @param request 생성할 카테고리 정보 DTO
	 * @return 카테고리 리스트 페이지로 리다이렉트
	 */
	@PostMapping
	public String createCategory(@Valid @ModelAttribute CreateCategoryRequest request) {
		categoryService.createCategory(request);
		return REDIRECT_URL;
	}

	/**
	 * 주어진 카테고리 ID에 해당하는 카테고리 정보를 수정합니다.
	 *
	 * @param categoryId 카테고리 ID
	 * @param request    수정할 카테고리 정보 DTO
	 * @return 카테고리 리스트 페이지로 리다이렉트
	 */
	@PutMapping("/{categoryId}")
	public String updateCategory(@PathVariable Long categoryId, @Valid @ModelAttribute UpdateCategoryRequest request) {
		categoryService.updateCategory(categoryId, request);
		return REDIRECT_URL;
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
		return REDIRECT_URL;
	}

	/**
	 * 주어진 검색 키워드를 기반으로 카테고리 정보를 검색합니다.
	 *
	 * @param search 검색할 키워드
	 * @return 검색 결과를 포함하는 List<CategorySearchResult> 객체를 반환합니다.
	 */
	@GetMapping("/search")
	public ResponseEntity<List<CategorySearchResult>> searchCategories(@RequestParam("key") String search) {
		List<CategorySearchResult> results = categoryService.searchCategories(search);
		return ResponseEntity.ok(results);
	}
}
