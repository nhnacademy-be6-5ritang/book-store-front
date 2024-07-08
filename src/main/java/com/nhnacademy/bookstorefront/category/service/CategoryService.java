package com.nhnacademy.bookstorefront.category.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstorefront.category.dto.response.CategorySearchResult;
import com.nhnacademy.bookstorefront.category.dto.response.CreateCategoryResponse;
import com.nhnacademy.bookstorefront.category.dto.response.GetCategoryResponse;
import com.nhnacademy.bookstorefront.category.dto.response.UpdateCategoryResponse;

/**
 * 외부 Category 서비스와 통신하여 카테고리 정보를 관리하는 인터페이스입니다.
 *
 * @version 1.0
 */
public interface CategoryService {

	/**
	 * 모든 카테고리 정보를 조회합니다.
	 *
	 * @return 모든 카테고리 정보 목록
	 */
	List<GetCategoryResponse> getCategories();

	/**
	 * 주어진 페이지 정보에 따라 카테고리 정보를 조회합니다.
	 *
	 * @param pageable 페이지 정보
	 * @return 페이지에 해당하는 카테고리 정보 목록
	 */
	Page<GetCategoryResponse> getCategories(Pageable pageable);

	/**
	 * 주어진 책 ID에 해당하는 카테고리 정보를 조회합니다.
	 *
	 * @param bookId 책 ID
	 * @return 해당 책에 속한 카테고리 정보 목록
	 */
	List<GetCategoryResponse> getCategoriesByBookId(Long bookId);

	/**
	 * 주어진 카테고리 ID에 해당하는 카테고리 정보를 조회합니다.
	 *
	 * @param categoryId 카테고리 ID
	 * @return 카테고리 정보
	 */
	GetCategoryResponse getCategory(Long categoryId);

	/**
	 * 새로운 카테고리를 생성합니다.
	 *
	 * @param request 생성할 카테고리 정보 DTO
	 * @return 생성된 카테고리 정보 DTO
	 */
	CreateCategoryResponse createCategory(CreateCategoryRequest request);

	/**
	 * 주어진 카테고리 ID에 해당하는 카테고리를 업데이트합니다.
	 *
	 * @param categoryId 카테고리 ID
	 * @param request   업데이트할 카테고리 정보 DTO
	 * @return 업데이트된 카테고리 정보 DTO
	 */
	UpdateCategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest request);

	/**
	 * 주어진 카테고리 ID에 해당하는 카테고리를 삭제합니다.
	 *
	 * @param categoryId 삭제할 카테고리 ID
	 */
	void deleteCategory(Long categoryId);


	List<CategorySearchResult> searchCategories(String query);
}
