package com.nhnacademy.bookstorefront.review.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.CreateReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.UpdateReviewResponse;

@FeignClient(name = "review-service", url = "http://localhost:8080")
public interface ReviewServiceClient {

	@GetMapping("/books/{bookId}/reviews/all")
	ResponseEntity<List<GetReviewResponse>> getReviews(@PathVariable Long bookId);

	@GetMapping("/books/{bookId}/reviews")
	ResponseEntity<Page<GetReviewResponse>> getReviewsByBookId(@RequestParam("userId") Long userId,
		@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(required = false) String sort,
		@PathVariable Long bookId);

	@PostMapping("/books/{bookId}/reviews")
	ResponseEntity<CreateReviewResponse> createReview(@PathVariable Long bookId,
		@RequestBody CreateReviewRequest request);

	@GetMapping("/books/{bookId}/reviews/{reviewId}")
	ResponseEntity<GetReviewResponse> getReview(@PathVariable Long bookId, @PathVariable Long reviewId);

	@PutMapping("/books/{bookId}/reviews/{reviewId}")
	ResponseEntity<UpdateReviewResponse> updateReview(@PathVariable Long bookId,
		@RequestBody UpdateReviewRequest request,
		@PathVariable Long reviewId);

	@DeleteMapping("/books/{bookId}/reviews/{reviewId}")
	ResponseEntity<Void> deleteReview(@PathVariable Long bookId, @PathVariable Long reviewId);
}
