package com.nhnacademy.bookstorefront.review.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.CreateReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.UpdateReviewResponse;
import com.nhnacademy.bookstorefront.review.feignclient.ReviewServiceClient;
import com.nhnacademy.bookstorefront.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewServiceClient reviewServiceClient;

	@Override
	public List<GetReviewResponse> getReviews(Long bookId) {
		return reviewServiceClient.getReviews(bookId).getBody();
	}

	@Override
	public Page<GetReviewResponse> getReviewsByBookId(Long userId, int page, int size, String sort, Long bookId) {
		return reviewServiceClient.getReviewsByBookId(userId, page, size, sort, bookId).getBody();
	}

	@Override
	public CreateReviewResponse createReview(Long bookId, CreateReviewRequest request) {
		return reviewServiceClient.createReview(bookId, request).getBody();
	}

	@Override
	public GetReviewResponse getReview(Long bookId, Long reviewId) {
		return reviewServiceClient.getReview(bookId, reviewId).getBody();
	}

	@Override
	public UpdateReviewResponse updateReview(Long bookId, UpdateReviewRequest request, Long reviewId) {
		return reviewServiceClient.updateReview(bookId, request, reviewId).getBody();
	}

	@Override
	public void deleteReview(Long bookId, Long reviewId) {

	}
}
