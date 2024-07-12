package com.nhnacademy.bookstorefront.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;

public interface ReviewService {
	Page<GetReviewResponse> getReviews(Pageable pageable);

	Page<GetReviewResponse> getReviewsByBookId(Pageable pageable, Long bookId);

	Page<GetReviewResponse> getPhotoReviewsByBookId(Pageable pageable, Long bookId);

	Page<GetReviewResponse> getGeneralReviewsByBookId(Pageable pageable, Long bookId);

	Page<GetReviewResponse> getReviewsByUserId(Pageable pageable);

	void createReview(CreateReviewRequest request, MultipartFile file);

	GetReviewResponse getReview(Long reviewId);

	void updateReview(UpdateReviewRequest request, Long reviewId);

	void deleteReview(Long reviewId);
}
