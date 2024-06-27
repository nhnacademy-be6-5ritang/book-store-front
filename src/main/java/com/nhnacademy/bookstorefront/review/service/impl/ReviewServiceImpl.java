package com.nhnacademy.bookstorefront.review.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public List<GetReviewResponse> getReviews() {
		return reviewServiceClient.getReviews().getBody();
	}

	@Override
	public Page<GetReviewResponse> getReviewsByBookId(Pageable pageable, Long bookId) {
		return reviewServiceClient.getReviewsByBookId(pageable, bookId).getBody();
	}

	@Override
	public CreateReviewResponse createReview(CreateReviewRequest request) {
		return reviewServiceClient.createReview(request).getBody();
	}

	@Override
	public GetReviewResponse getReview(Long reviewId) {
		return reviewServiceClient.getReview(reviewId).getBody();
	}

	@Override
	public UpdateReviewResponse updateReview(UpdateReviewRequest request, Long reviewId) {
		return reviewServiceClient.updateReview(request, reviewId).getBody();
	}

	@Override
	public void deleteReview(Long bookId, Long reviewId) {
		reviewServiceClient.deleteReview(bookId, reviewId);
	}
}
