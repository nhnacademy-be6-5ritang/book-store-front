package com.nhnacademy.bookstorefront.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.CreateReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.UpdateReviewResponse;

public interface ReviewService {
	Page<GetReviewResponse> getReviews(Pageable pageable);

	Page<GetReviewResponse> getReviewsByBookId(Pageable pageable, Long bookId);

	Page<GetReviewResponse> getReviewsByUserId(Pageable pageable);

	CreateReviewResponse createReview(CreateReviewRequest request);

	GetReviewResponse getReview(Long reviewId);

	UpdateReviewResponse updateReview(UpdateReviewRequest request, Long reviewId);

	void deleteReview(Long bookId, Long reviewId);
}
