package com.nhnacademy.bookstorefront.review.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.point.feignclient.PointServiceClient;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetBookOrderWithoutReviewResponse;
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
	private final PointServiceClient pointServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetReviewResponse> getReviews(Pageable pageable) {
		return reviewServiceClient.getReviews(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetReviewResponse> getPhotoReviews(Pageable pageable) {
		return reviewServiceClient.getPhotoReviews(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetReviewResponse> getGeneralReviews(Pageable pageable) {
		return reviewServiceClient.getGeneralReviews(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetReviewResponse> getReviewsByBookId(Pageable pageable, Long bookId) {
		return reviewServiceClient.getReviewsByBookId(pageable, bookId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetReviewResponse> getGeneralReviewsByBookId(Pageable pageable, Long bookId) {
		return reviewServiceClient.getGeneralReviewsByBookId(pageable, bookId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetReviewResponse> getPhotoReviewsByBookId(Pageable pageable, Long bookId) {
		return reviewServiceClient.getPhotoReviewsByBookId(pageable, bookId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetReviewResponse> getReviewsByUserId(Pageable pageable) {
		return reviewServiceClient.getReviewsByUserId(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetReviewResponse> getGeneralReviewsByUserId(Pageable pageable) {
		return reviewServiceClient.getGeneralReviewsByUserId(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetReviewResponse> getPhotoReviewsByUserId(Pageable pageable) {
		return reviewServiceClient.getPhotoReviewsByUserId(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public GetReviewResponse getReview(Long reviewId) {
		return reviewServiceClient.getReview(reviewId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void createReview(CreateReviewRequest request, MultipartFile file) {
		String fileName = null;
		String reviewType = "REVIEW";
		if (!file.isEmpty()) {
			fileName = uploadServiceClient.upload(file, "reviews").getBody();
			reviewType = "PHOTO_REVIEW";
		}
		reviewServiceClient.createReview(CreateReviewRequest.from(request, fileName));
		pointServiceClient.reviewPointTransaction(reviewType);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void updateReview(UpdateReviewRequest request, Long reviewId, MultipartFile file) {
		String fileName = null;
		if (!file.isEmpty()) {
			fileName = uploadServiceClient.upload(file, "reviews").getBody();
		}
		reviewServiceClient.updateReview(UpdateReviewRequest.from(request, fileName), reviewId);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void deleteReview(Long reviewId) {
		reviewServiceClient.deleteReview(reviewId);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public double getReviewsAverageScoreByBookId(Long bookId) {
		return reviewServiceClient.getReviewsAverageScoreByBookId(bookId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<GetBookOrderWithoutReviewResponse> getBooksWithoutReviews() {
		return reviewServiceClient.getBooksWithoutReviews().getBody();
	}

}
