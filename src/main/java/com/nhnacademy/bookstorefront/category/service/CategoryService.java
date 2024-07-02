package com.nhnacademy.bookstorefront.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.response.CreateCategoryResponse;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.dto.response.UpdateCategoryResponse;
import com.nhnacademy.bookstorefront.category.feignclient.CategoryServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryServiceClient categoryServiceClient;

	public List<GetCategoryResponse> getCategories() {
		return categoryServiceClient.getCategories().getBody();
	}

	public List<GetCategoryResponse> getCategoriesByBookId(Long bookId) {
		return categoryServiceClient.getCategoriesByBookId(bookId).getBody();
	}

	public GetCategoryResponse getCategory(Long categoryId) {
		return categoryServiceClient.getCategory(categoryId).getBody();
	}

	public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
		return categoryServiceClient.createCategory(request).getBody();
	}

	public UpdateCategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest request) {
		return categoryServiceClient.updateCategory(categoryId, request).getBody();
	}

	public void deleteCategory(Long categoryId) {
		categoryServiceClient.deleteCategory(categoryId);
	}
}
