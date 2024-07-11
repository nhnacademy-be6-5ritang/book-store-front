package com.nhnacademy.bookstorefront.review.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.CreateReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;

@FeignClient(name = "review-feign-client", url = "http://localhost:8090/api")
public interface ReviewServiceClient {

	@GetMapping("/reviews/page")
	ResponseEntity<Page<GetReviewResponse>> getReviews(Pageable pageable);

	@GetMapping("/books/{bookId}/reviews/page")
	ResponseEntity<Page<GetReviewResponse>> getReviewsByBookId(Pageable pageable,
		@PathVariable Long bookId);

	@GetMapping("/users/me/reviews/page")
	ResponseEntity<Page<GetReviewResponse>> getReviewsByUserId(Pageable pageable);

	@PostMapping(value = "/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<CreateReviewResponse> createReview(@ModelAttribute CreateReviewRequest request,
		@RequestPart(value = "image", required = false) MultipartFile image);

	@GetMapping("/reviews/{reviewId}")
	ResponseEntity<GetReviewResponse> getReview(@PathVariable Long reviewId);

	@PutMapping("/reviews/{reviewId}")
	ResponseEntity<Void> updateReview(@RequestBody UpdateReviewRequest request,
		@PathVariable Long reviewId);

	@DeleteMapping("/reviews/{reviewId}")
	ResponseEntity<Void> deleteReview(@PathVariable Long reviewId);
}
