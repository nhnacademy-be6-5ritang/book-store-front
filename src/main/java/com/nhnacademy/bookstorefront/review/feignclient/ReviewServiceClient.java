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

import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.CreateReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.UpdateReviewResponse;

@FeignClient(name = "review-feign-client", url = "http://localhost:8083")
public interface ReviewServiceClient {

	@GetMapping("/reviews")
	ResponseEntity<List<GetReviewResponse>> getReviews();

	@GetMapping("/books/{bookId}/reviews")
	ResponseEntity<Page<GetReviewResponse>> getReviewsByBookId(Pageable pageable,
		@PathVariable Long bookId);

	@PostMapping("/reviews")
	ResponseEntity<CreateReviewResponse> createReview(@RequestBody CreateReviewRequest request);

	@GetMapping("/reviews/{reviewId}")
	ResponseEntity<GetReviewResponse> getReview(@PathVariable Long reviewId);

	@PutMapping("/reviews/{reviewId}")
	ResponseEntity<UpdateReviewResponse> updateReview(@RequestBody UpdateReviewRequest request,
		@PathVariable Long reviewId);

	@DeleteMapping("/books/{bookId}/reviews/{reviewId}")
	ResponseEntity<Void> deleteReview(@PathVariable Long bookId, @PathVariable Long reviewId);
}
