package com.nhnacademy.bookstorefront.category.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.response.CategorySearchResult;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;

@FeignClient(name = "category-feign-client", url = "http://localhost:8090/api/categories")
public interface CategoryServiceClient {

	@GetMapping
	ResponseEntity<List<GetCategoryResponse>> getCategories();

	@GetMapping("/page")
	ResponseEntity<Page<GetCategoryResponse>> getCategories(Pageable pageable);

	@GetMapping("/books/{bookId}")
	ResponseEntity<List<GetCategoryResponse>> getCategoriesByBookId(@PathVariable Long bookId);

	@GetMapping("/{categoryId}")
	ResponseEntity<GetCategoryResponse> getCategory(@PathVariable Long categoryId);

	@PostMapping
	ResponseEntity<Void> createCategory(
		@RequestBody CreateCategoryRequest request);

	@PutMapping("/{categoryId}")
	ResponseEntity<Void> updateCategory(@PathVariable Long categoryId,
		@RequestBody UpdateCategoryRequest request);

	@DeleteMapping("/{categoryId}")
	ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId);

	@GetMapping("/search")
	ResponseEntity<List<CategorySearchResult>> searchCategories(@RequestParam("key") String search);
}
