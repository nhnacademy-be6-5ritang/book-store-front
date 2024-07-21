package com.nhnacademy.bookstorefront.review.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookTitleResponse;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;

@FeignClient(name = "review-feign-client", url = "http://localhost:8090/api")
public interface ReviewServiceClient {

	@GetMapping("/reviews/page")
	ResponseEntity<Page<GetReviewResponse>> getReviews(Pageable pageable);

	@GetMapping("/books/{bookId}/reviews/all/page")
	ResponseEntity<Page<GetReviewResponse>> getReviewsByBookId(Pageable pageable,
		@PathVariable Long bookId);

	@GetMapping("/books/{bookId}/reviews/general/page")
	ResponseEntity<Page<GetReviewResponse>> getGeneralReviewsByBookId(Pageable pageable,
		@PathVariable Long bookId);

	@GetMapping("/books/{bookId}/reviews/photo/page")
	ResponseEntity<Page<GetReviewResponse>> getPhotoReviewsByBookId(Pageable pageable,
		@PathVariable Long bookId);

	@GetMapping("/users/me/reviews/all/page")
	ResponseEntity<Page<GetReviewResponse>> getReviewsByUserId(Pageable pageable);

	@GetMapping("/users/me/reviews/general/page")
	ResponseEntity<Page<GetReviewResponse>> getGeneralReviewsByUserId(Pageable pageable);

	@GetMapping("/users/me/reviews/photo/page")
	ResponseEntity<Page<GetReviewResponse>> getPhotoReviewsByUserId(Pageable pageable);

	@PostMapping("/reviews")
	ResponseEntity<Void> createReview(@RequestBody CreateReviewRequest request);

	@GetMapping("/reviews/{reviewId}")
	ResponseEntity<GetReviewResponse> getReview(@PathVariable Long reviewId);

	@PutMapping("/reviews/{reviewId}")
	ResponseEntity<Void> updateReview(@RequestBody UpdateReviewRequest request,
		@PathVariable Long reviewId);

	@DeleteMapping("/reviews/{reviewId}")
	ResponseEntity<Void> deleteReview(@PathVariable Long reviewId);

	@GetMapping("/books/{bookId}/reviews/average")
	ResponseEntity<Double> getReviewsAverageScoreByBookId(@PathVariable Long bookId);

	@GetMapping("/reviews/create/possible")
	ResponseEntity<List<GetBookTitleResponse>> getBooksByOrderStatusCompletionAndUserId();
}
