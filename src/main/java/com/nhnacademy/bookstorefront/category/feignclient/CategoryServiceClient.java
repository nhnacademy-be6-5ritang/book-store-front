package com.nhnacademy.bookstorefront.category.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.response.CreateCategoryResponse;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.dto.response.UpdateCategoryResponse;

@FeignClient(name = "category-feign-client", url = "http://localhost:8083")
public interface CategoryServiceClient {

	@GetMapping("/categories")
	ResponseEntity<List<GetCategoryResponse>> getCategories();

	@GetMapping("/categories/{categoryId}")
	ResponseEntity<GetCategoryResponse> getCategory(@PathVariable Long categoryId);

	@PostMapping("/categories")
	ResponseEntity<CreateCategoryResponse> createCategory(
		@RequestBody CreateCategoryRequest request);

	@PutMapping("/categories/{categoryId}")
	ResponseEntity<UpdateCategoryResponse> updateCategory(@PathVariable Long categoryId,
		@RequestBody UpdateCategoryRequest request);

	@DeleteMapping("/categories/{categoryId}")
	ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId);
}
