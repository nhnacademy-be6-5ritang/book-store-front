package com.nhnacademy.bookstorefront.review.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.feignclient.ReviewServiceClient;
import com.nhnacademy.bookstorefront.review.service.ReviewService;
import com.nhnacademy.bookstorefront.upload.feignclient.UploadServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewServiceClient reviewServiceClient;
	private final UploadServiceClient uploadServiceClient;

	@Override
	public Page<GetReviewResponse> getReviews(Pageable pageable) {
		return reviewServiceClient.getReviews(pageable).getBody();
	}

	@Override
	public Page<GetReviewResponse> getReviewsByBookId(Pageable pageable, Long bookId) {
		return reviewServiceClient.getReviewsByBookId(pageable, bookId).getBody();
	}

	@Override
	public Page<GetReviewResponse> getGeneralReviewsByBookId(Pageable pageable, Long bookId) {
		return reviewServiceClient.getGeneralReviewsByBookId(pageable, bookId).getBody();
	}

	@Override
	public Page<GetReviewResponse> getPhotoReviewsByBookId(Pageable pageable, Long bookId) {
		return reviewServiceClient.getPhotoReviewsByBookId(pageable, bookId).getBody();
	}

	@Override
	public Page<GetReviewResponse> getReviewsByUserId(Pageable pageable) {
		return reviewServiceClient.getReviewsByUserId(pageable).getBody();
	}

	@Override
	public Page<GetReviewResponse> getGeneralReviewsByUserId(Pageable pageable) {
		return reviewServiceClient.getGeneralReviewsByUserId(pageable).getBody();
	}

	@Override
	public Page<GetReviewResponse> getPhotoReviewsByUserId(Pageable pageable) {
		return reviewServiceClient.getPhotoReviewsByUserId(pageable).getBody();
	}

	@Override
	public void createReview(CreateReviewRequest request, MultipartFile file) {
		String fileName = null;
		if (!file.isEmpty()) {
			fileName = uploadServiceClient.upload(file).getBody();
		}

		reviewServiceClient.createReview(CreateReviewRequest.from(request, fileName));
	}

	@Override
	public GetReviewResponse getReview(Long reviewId) {
		return reviewServiceClient.getReview(reviewId).getBody();
	}

	@Override
	public void updateReview(UpdateReviewRequest request, Long reviewId) {
		reviewServiceClient.updateReview(request, reviewId);
	}

	@Override
	public void deleteReview(Long reviewId) {
		reviewServiceClient.deleteReview(reviewId);
	}
}
