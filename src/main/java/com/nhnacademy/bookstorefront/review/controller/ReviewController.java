package com.nhnacademy.bookstorefront.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/reviews/create")
	public String createReview() {
		return "review/create-review";
	}

	@GetMapping("/reviews")
	public String getReviews(Model model) {
		model.addAttribute("reviews", reviewService.getReviews());
		return "review/list-review";
	}

	@GetMapping("/books/{bookId}/reviews")
	public String getBookReviews(@PageableDefault(page = 1) Pageable pageable, @PathVariable Long bookId, Model model) {
		Page<GetReviewResponse> reviews = reviewService.getReviewsByBookId(pageable, bookId);
		model.addAttribute("reviews", reviews);

		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), reviews.getTotalPages());

		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "review/list-review";
	}
}
