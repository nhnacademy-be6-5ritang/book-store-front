package com.nhnacademy.bookstorefront.product.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.product.dto.response.GetProductSimpleResponse;
import com.nhnacademy.bookstorefront.product.service.ProductService;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

/**
 * @author 김기욱, 이경헌
 * 상품 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	private final ProductService productService;
	private final ReviewService reviewService;

	/**
	 * 주어진 도서 ID에 해당하는 상품 정보를 조회합니다.
	 *
	 * @param bookId 책 ID
	 * @param model  모델 객체
	 * @return 책 정보 뷰 이름
	 */
	@GetMapping("/{bookId}")
	public String getProduct(@PathVariable Long bookId, Model model,
		@PageableDefault(page = 1, size = 5) Pageable pageable,
		@RequestParam(required = false, defaultValue = "전체") String reviewType) {
		model.addAttribute("product", productService.getProduct(bookId));

		Page<GetReviewResponse> reviews;
		if (reviewType != null && reviewType.equals("일반")) {
			reviews = reviewService.getGeneralReviewsByBookId(pageable, bookId);
		} else if (reviewType != null && reviewType.equals("사진")) {
			reviews = reviewService.getPhotoReviewsByBookId(pageable, bookId);
		} else {
			reviews = reviewService.getReviewsByBookId(pageable, bookId);
		}

		model.addAttribute("reviewType", reviewType);
		model.addAttribute("reviews", reviews);
		PagingModel.pagingProcessing(pageable, model, reviews, "/products/" + bookId + "?reviewType=" + reviewType, 5);

		return "product/get-product";
	}

	@GetMapping("/category")
	public String getBooksByCategoryName(
		@PageableDefault(page = 1, size = 12, sort = {
			"bookPublishDate"}, direction = Sort.Direction.DESC) Pageable defaultPageable,
		@RequestParam(defaultValue = "bookPublishDate") String sortBy,
		@RequestParam(defaultValue = "DESC") String direction,
		@RequestParam String categoryName, Model model) {

		Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
		Sort sort = Sort.by(sortDirection, sortBy);
		Pageable pageable = PageRequest.of(defaultPageable.getPageNumber(), defaultPageable.getPageSize(), sort);

		Page<GetProductSimpleResponse> products = productService.getBooksByCategory(pageable, categoryName);
		model.addAttribute("products", products);
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		PagingModel.pagingProcessing(pageable, model, products,
			"/products/category?categoryName=" + categoryName + "&direction=" + direction + "&sortBy=" + sortBy, 5);

		return "product/list-product-by-category";
	}

}
