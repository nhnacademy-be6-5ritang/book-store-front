package com.nhnacademy.bookstorefront.review.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.CreateReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.UpdateReviewResponse;

public interface ReviewService {
	List<GetReviewResponse> getReviews(Long bookId);

	Page<GetReviewResponse> getReviewsByBookId(Long userId, int page, int size, String sort, Long bookId);

	CreateReviewResponse createReview(Long bookId, CreateReviewRequest request);

	GetReviewResponse getReview(Long bookId, Long reviewId);

	UpdateReviewResponse updateReview(Long bookId, UpdateReviewRequest request, Long reviewId);

	void deleteReview(Long bookId, Long reviewId);
}
