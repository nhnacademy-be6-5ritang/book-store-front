package com.nhnacademy.bookstorefront.category.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.response.CreateCategoryResponse;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.dto.response.UpdateCategoryResponse;
import com.nhnacademy.bookstorefront.category.feignclient.CategoryServiceClient;
import com.nhnacademy.bookstorefront.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryServiceClient categoryServiceClient;

	@Override
	public List<GetCategoryResponse> getCategories() {
		return categoryServiceClient.getCategories().getBody();
	}

	@Override
	public Page<GetCategoryResponse> getCategories(Pageable pageable) {
		return categoryServiceClient.getCategories(pageable).getBody();
	}

	@Override
	public List<GetCategoryResponse> getCategoriesByBookId(Long bookId) {
		return categoryServiceClient.getCategoriesByBookId(bookId).getBody();
	}

	@Override
	public GetCategoryResponse getCategory(Long categoryId) {
		return categoryServiceClient.getCategory(categoryId).getBody();
	}

	@Override
	public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
		return categoryServiceClient.createCategory(request).getBody();
	}

	@Override
	public UpdateCategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest request) {
		return categoryServiceClient.updateCategory(categoryId, request).getBody();
	}

	@Override
	public void deleteCategory(Long categoryId) {
		categoryServiceClient.deleteCategory(categoryId);
	}
}
