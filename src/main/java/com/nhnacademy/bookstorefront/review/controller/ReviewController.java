package com.nhnacademy.bookstorefront.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/reviews/create")
	public String createReview() {
		return "review/create-review";
	}

	@GetMapping("/reviews/page")
	public String getReviews(@PageableDefault(page = 1) Pageable pageable, Model model) {
		Page<GetReviewResponse> reviews = reviewService.getReviews(pageable);
		model.addAttribute("reviews", reviews);
		model.addAttribute("objects", reviews); // 공통 객체 이름
		model.addAttribute("baseUrl", "/api/reviews/page"); // 페이징 URL

		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), reviews.getTotalPages());

		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "review/list-all-review";
	}

	@GetMapping("/books/{bookId}/reviews/page")
	public String getBookReviews(@PageableDefault(page = 1) Pageable pageable, @PathVariable Long bookId, Model model) {
		Page<GetReviewResponse> reviews = reviewService.getReviewsByBookId(pageable, bookId);
		model.addAttribute("reviews", reviews);
		model.addAttribute("objects", reviews); // 공통 객체 이름
		model.addAttribute("baseUrl", "/api/books/" + bookId + "/reviews/page"); // 페이징 URL

		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), reviews.getTotalPages());

		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "review/list-by-book-review";
	}

	@GetMapping("/users/me/reviews/page")
	public String getBookReviews(@PageableDefault(page = 1) Pageable pageable, Model model) {
		Page<GetReviewResponse> reviews = reviewService.getReviewsByUserId(pageable);
		model.addAttribute("reviews", reviews);
		model.addAttribute("objects", reviews); // 공통 객체 이름
		model.addAttribute("baseUrl", "/api/users/me/reviews/page"); // 페이징 URL

		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), reviews.getTotalPages());

		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "review/list-by-user-review";
	}
}
