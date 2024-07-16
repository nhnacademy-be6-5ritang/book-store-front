package com.nhnacademy.bookstorefront.review.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookTitleResponse;
import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌
 * 리뷰 관련 기능을 담당하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
	private final ReviewService reviewService;
	private final BookService bookService;
	private static final String REDIRECT_URL = "redirect:/api/users/me/reviews/page";

	/**
	 * 책에 대한 리뷰 작성 페이지로 이동합니다.
	 *
	 * @param bookId 책 ID
	 * @param model  데이터 모델
	 * @return 리뷰 작성 페이지의 뷰 이름
	 */
	@GetMapping("/reviews/create/{bookId}")
	public String createReview(@PathVariable Long bookId, Model model) {
		GetBookDetailResponse book = bookService.getBook(bookId);
		model.addAttribute("book", book);
		return "review/create-review";
	}

	/**
	 * 모든 리뷰 목록을 페이징하여 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @param model    데이터 모델
	 * @return 모든 리뷰 목록 페이지의 뷰 이름
	 */
	@GetMapping("/reviews/page")
	public String getReviews(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model) {
		Page<GetReviewResponse> reviews = reviewService.getReviews(pageable);
		model.addAttribute("reviews", reviews);
		PagingModel.pagingProcessing(pageable, model, reviews, "/api/reviews/page", 5);
		return "review/list-all-review";
	}

	/**
	 * 특정 책에 대한 리뷰 목록을 페이징하여 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @param bookId   책 ID
	 * @param model    데이터 모델
	 * @return 특정 책에 대한 리뷰 목록 페이지의 뷰 이름
	 */
	@GetMapping("/books/{bookId}/reviews/page")
	public String getBookReviews(@PageableDefault(page = 1, size = 5) Pageable pageable, @PathVariable Long bookId,
		Model model) {
		Page<GetReviewResponse> reviews = reviewService.getReviewsByBookId(pageable, bookId);
		model.addAttribute("reviews", reviews);
		PagingModel.pagingProcessing(pageable, model, reviews, "/api/books/" + bookId + "/reviews/page", 5);
		return "review/list-by-book-review";
	}

	/**
	 * 리뷰를 생성합니다.
	 *
	 * @param request 리뷰 생성 요청 객체
	 * @param file    첨부 파일
	 * @return 리뷰 목록 페이지로 리다이렉트하는 URL
	 */
	@PostMapping("/reviews")
	public String createReview(@ModelAttribute CreateReviewRequest request,
		@RequestParam("file") MultipartFile file) {
		reviewService.createReview(request, file);
		return REDIRECT_URL;
	}

	/**
	 * 리뷰를 수정합니다.
	 *
	 * @param request  리뷰 수정 요청 객체
	 * @param reviewId 리뷰 ID
	 * @return 리뷰 목록 페이지로 리다이렉트하는 URL
	 */
	@PutMapping("/reviews/{reviewId}")
	public String updateReview(@ModelAttribute UpdateReviewRequest request, @PathVariable Long reviewId) {
		reviewService.updateReview(request, reviewId);
		return REDIRECT_URL;
	}

	/**
	 * 사용자의 리뷰 목록을 페이징하여 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @param model    데이터 모델
	 * @return 사용자의 리뷰 목록 페이지의 뷰 이름
	 */
	@GetMapping("/users/me/reviews/page")
	public String getBookReviews(@PageableDefault(page = 1, size = 5) Pageable pageable, Model model) {
		String baseUrl = "/api/users/me/reviews/page";
		Page<GetReviewResponse> reviews = reviewService.getReviewsByUserId(pageable);
		model.addAttribute("reviews", reviews);
		PagingModel.pagingProcessing(pageable, model, reviews, baseUrl, 5);

		Page<GetReviewResponse> generalReviews = reviewService.getGeneralReviewsByUserId(pageable);
		model.addAttribute("generalReviews", generalReviews);
		PagingModel.pagingProcessing(pageable, model, generalReviews, baseUrl, 5);

		Page<GetReviewResponse> photoReviews = reviewService.getPhotoReviewsByUserId(pageable);
		model.addAttribute("photoReviews", photoReviews);
		PagingModel.pagingProcessing(pageable, model, photoReviews, baseUrl, 5);

		List<GetBookTitleResponse> possibleBooks = reviewService.getBooksByOrderStatusCompletionAndUserId();
		model.addAttribute("possibleBooks", possibleBooks);

		return "review/list-by-user-review";
	}

	/**
	 * 리뷰를 삭제합니다.
	 *
	 * @param reviewsId 삭제할 리뷰 ID
	 * @return 사용자의 리뷰 목록 페이지로 리다이렉트하는 URL
	 */
	@DeleteMapping("/reviews/{reviewsId}")
	public String deleteReview(@PathVariable Long reviewsId) {
		reviewService.deleteReview(reviewsId);
		return REDIRECT_URL;
	}
}
