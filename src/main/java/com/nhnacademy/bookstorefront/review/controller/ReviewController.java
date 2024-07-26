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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.service.BookOrderService;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetBookOrderWithoutReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;

import jakarta.validation.Valid;
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
	private static final String REDIRECT_URL = "redirect:/api/users/me/reviews/page";
	private final BookOrderService bookOrderService;

	/**
	 * 책에 대한 리뷰 작성 페이지로 이동합니다.
	 *
	 * @param bookOrderId 도서 주문 ID
	 * @param model  데이터 모델
	 * @return 리뷰 작성 페이지의 뷰 이름
	 */
	@GetMapping("/reviews/create/{bookOrderId}")
	public String createReview(@PathVariable Long bookOrderId, Model model) {
		GetBookOrderResponse orderBook = bookOrderService.getBookOrder(bookOrderId);
		GetBookOrderGetBookResponse book = orderBook.getBookResponse();
		model.addAttribute("orderBook", orderBook);
		model.addAttribute("book", book);
		return "review/create-review";
	}

	/**
	 * 리뷰 수정 페이지로 이동합니다.
	 *
	 * @param reviewId 리뷰 ID
	 * @param model  데이터 모델
	 * @return 리뷰 작성 페이지의 뷰 이름
	 */
	@GetMapping("/reviews/update/{reviewId}")
	public String updateReview(@PathVariable Long reviewId, Model model) {
		GetReviewResponse review = reviewService.getReview(reviewId);
		model.addAttribute("review", review);
		return "review/update-review";
	}

	/**
	 * 모든 리뷰 목록을 페이징하여 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @param model    데이터 모델
	 * @return 모든 리뷰 목록 페이지의 뷰 이름
	 */
	@GetMapping("/reviews/page")
	public String getReviews(@PageableDefault(page = 1, size = 5) Pageable pageable, Model model,
		@RequestParam(required = false, defaultValue = "전체") String reviewType) {

		Page<GetReviewResponse> reviews;
		if (reviewType != null && reviewType.equals("일반")) {
			reviews = reviewService.getGeneralReviews(pageable);
		} else if (reviewType != null && reviewType.equals("사진")) {
			reviews = reviewService.getPhotoReviews(pageable);
		} else {
			reviews = reviewService.getReviews(pageable);
		}

		model.addAttribute("reviewType", reviewType);
		model.addAttribute("reviews", reviews);
		PagingModel.pagingProcessing(pageable, model, reviews,
			"/api/reviews/page" + "?reviewType=" + reviewType, 5);

		return "review/list-all-review";
	}

	/**
	 * 사용자의 리뷰 목록을 페이징하여 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @param model    데이터 모델
	 * @return 사용자의 리뷰 목록 페이지의 뷰 이름
	 */
	@GetMapping("/users/me/reviews/page")
	public String getReviewsByUserId(@PageableDefault(page = 1, size = 5) Pageable pageable, Model model,
		@RequestParam(required = false, defaultValue = "전체") String reviewType) {

		List<GetBookOrderWithoutReviewResponse> possibleBooks = reviewService.getBooksWithoutReviews();
		model.addAttribute("possibleBooks", possibleBooks);

		Page<GetReviewResponse> reviews;
		if (reviewType != null && reviewType.equals("일반")) {
			reviews = reviewService.getGeneralReviewsByUserId(pageable);
		} else if (reviewType != null && reviewType.equals("사진")) {
			reviews = reviewService.getPhotoReviewsByUserId(pageable);
		} else {
			reviews = reviewService.getReviewsByUserId(pageable);
		}

		model.addAttribute("reviewType", reviewType);
		model.addAttribute("reviews", reviews);
		PagingModel.pagingProcessing(pageable, model, reviews,
			"/api/users/me/reviews/page" + "?reviewType=" + reviewType, 5);

		return "review/list-by-user-review";
	}

	@GetMapping("/reviews/book/{reviewId}")
	public String getReviewByBook(@PathVariable Long reviewId, Model model) {
		GetReviewResponse review = reviewService.getReview(reviewId);
		model.addAttribute("review", review);
		return "review/get-review-by-book";
	}

	@GetMapping("/reviews/user/{reviewId}")
	public String getReviewByUser(@PathVariable Long reviewId, Model model) {
		GetReviewResponse review = reviewService.getReview(reviewId);
		model.addAttribute("review", review);
		return "review/get-review-by-user";
	}

	/**
	 * 리뷰를 생성합니다.
	 *
	 * @param request 리뷰 생성 요청 객체
	 * @param file    첨부 파일
	 * @return 리뷰 목록 페이지로 리다이렉트하는 URL
	 */
	@PostMapping("/reviews")
	public String createReview(@Valid @ModelAttribute CreateReviewRequest request,
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
	@PostMapping("/reviews/{reviewId}")
	public String updateReview(@Valid @ModelAttribute UpdateReviewRequest request, @PathVariable Long reviewId,
		@RequestParam("file") MultipartFile file) {
		reviewService.updateReview(request, reviewId, file);
		return REDIRECT_URL;
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
