package com.nhnacademy.bookstorefront.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
	private final ReviewService reviewService;
	private final BookService bookService;

	@GetMapping("/reviews/create")
	public String createReview(@RequestParam Long bookId, Model model) {
		GetBookDetailResponse book = bookService.getBook(bookId);
		model.addAttribute("book", book);
		return "review/create-review";
	}

	@GetMapping("/reviews/page")
	public String getReviews(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model) {
		Page<GetReviewResponse> reviews = reviewService.getReviews(pageable);
		model.addAttribute("reviews", reviews);
		PagingModel.pagingProcessing(pageable, model, reviews, "/api/reviews/page", 5);

		return "review/list-all-review";
	}

	@GetMapping("/books/{bookId}/reviews/page")
	public String getBookReviews(@PageableDefault(page = 1, size = 5) Pageable pageable, @PathVariable Long bookId,
		Model model) {
		Page<GetReviewResponse> reviews = reviewService.getReviewsByBookId(pageable, bookId);
		model.addAttribute("reviews", reviews);
		PagingModel.pagingProcessing(pageable, model, reviews, "/api/books/" + bookId + "/reviews/page", 5);

		return "review/list-by-book-review";
	}

	@PostMapping("/reviews")
	public String createReview(@ModelAttribute CreateReviewRequest request) {
		reviewService.createReview(request);
		return "redirect:/users/me/reviews/page";
	}

	@PutMapping("/reviews/{reviewId}")
	public String updateReview(@ModelAttribute UpdateReviewRequest request, @PathVariable Long reviewId) {
		reviewService.updateReview(request, reviewId);
		return "redirect:/users/me/reviews/page";
	}

	@GetMapping("/users/me/reviews/page")
	public String getBookReviews(@PageableDefault(page = 1, size = 5) Pageable pageable, Model model) {
		Page<GetReviewResponse> reviews = reviewService.getReviewsByUserId(pageable);
		model.addAttribute("reviews", reviews);
		PagingModel.pagingProcessing(pageable, model, reviews, "/api/users/me/reviews/page", 5);

		return "review/list-by-user-review";
	}
}
