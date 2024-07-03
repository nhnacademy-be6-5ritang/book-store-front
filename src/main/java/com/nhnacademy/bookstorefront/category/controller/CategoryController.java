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
import com.nhnacademy.bookstorefront.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
	private final CategoryService categoryService;

	@GetMapping("/create")
	public String createCategoryForm(Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		return "category/create-category";
	}

	@GetMapping("/update/{categoryId}")
	public String updateCategoryForm(@PathVariable Long categoryId, Model model) {
		model.addAttribute("category", categoryService.getCategory(categoryId));
		model.addAttribute("categories", categoryService.getCategories());
		return "category/update-category";
	}

	@GetMapping
	public String getCategories(Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		return "category/list-category";
	}

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

	@PostMapping
	public String createCategory(@ModelAttribute CreateCategoryRequest request) {
		categoryService.createCategory(request);
		return "redirect:/api/categories/page";
	}

	@PutMapping("/{categoryId}")
	public String updateCategory(@PathVariable Long categoryId, @ModelAttribute UpdateCategoryRequest request) {
		categoryService.updateCategory(categoryId, request);
		return "redirect:/api/categories/page";
	}

	@DeleteMapping("/{categoryId}")
	public String deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return "redirect:/api/categories/page";
	}
}
