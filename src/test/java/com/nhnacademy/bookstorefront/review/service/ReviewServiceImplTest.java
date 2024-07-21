package com.nhnacademy.bookstorefront.review.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookTitleResponse;
import com.nhnacademy.bookstorefront.point.feignclient.PointServiceClient;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.feignclient.ReviewServiceClient;
import com.nhnacademy.bookstorefront.upload.feignclient.UploadServiceClient;

class ReviewServiceImplTest {

	@InjectMocks
	private ReviewServiceImpl reviewService;

	@Mock
	private ReviewServiceClient reviewServiceClient;

	@Mock
	private UploadServiceClient uploadServiceClient;

	@Mock
	private PointServiceClient pointServiceClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetReviews() {
		Pageable pageable = Pageable.ofSize(10);
		Page<GetReviewResponse> reviewPage = mock(Page.class);
		when(reviewServiceClient.getReviews(pageable)).thenReturn(ResponseEntity.ok(reviewPage));

		Page<GetReviewResponse> result = reviewService.getReviews(pageable);

		verify(reviewServiceClient).getReviews(pageable);
		assertEquals(reviewPage, result);
	}

	@Test
	void testGetReviewsByBookId() {
		Pageable pageable = Pageable.ofSize(10);
		Long bookId = 1L;
		Page<GetReviewResponse> reviewPage = mock(Page.class);
		when(reviewServiceClient.getReviewsByBookId(pageable, bookId)).thenReturn(ResponseEntity.ok(reviewPage));

		Page<GetReviewResponse> result = reviewService.getReviewsByBookId(pageable, bookId);

		verify(reviewServiceClient).getReviewsByBookId(pageable, bookId);
		assertEquals(reviewPage, result);
	}

	@Test
	void testCreateReview() {
		CreateReviewRequest request = CreateReviewRequest.builder()
			.bookId(1L)
			.reviewScore(5)
			.reviewComment("Great book!")
			.build();

		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(false);
		when(uploadServiceClient.upload(file)).thenReturn(ResponseEntity.ok("uploaded-file-name"));

		reviewService.createReview(request, file);

		verify(uploadServiceClient).upload(file);
		verify(reviewServiceClient).createReview(CreateReviewRequest.from(request, "uploaded-file-name"));
		verify(pointServiceClient).reviewPointTransaction("PHOTO_REVIEW");
	}

	@Test
	void testUpdateReview() {
		UpdateReviewRequest request = new UpdateReviewRequest(5, "Updated review comment");
		Long reviewId = 1L;

		reviewService.updateReview(request, reviewId);

		verify(reviewServiceClient).updateReview(request, reviewId);
	}

	@Test
	void testDeleteReview() {
		Long reviewId = 1L;

		reviewService.deleteReview(reviewId);

		verify(reviewServiceClient).deleteReview(reviewId);
	}

	@Test
	void testGetReview() {
		Long reviewId = 1L;
		GetReviewResponse reviewResponse = new GetReviewResponse(1L, "user", 5, "Great book!", LocalDateTime.now(),
			"image-url");
		when(reviewServiceClient.getReview(reviewId)).thenReturn(ResponseEntity.ok(reviewResponse));

		GetReviewResponse result = reviewService.getReview(reviewId);

		verify(reviewServiceClient).getReview(reviewId);
		assertEquals(reviewResponse, result);
	}

	@Test
	void testGetReviewsAverageScoreByBookId() {
		Long bookId = 1L;
		double averageScore = 4.5;
		when(reviewServiceClient.getReviewsAverageScoreByBookId(bookId)).thenReturn(ResponseEntity.ok(averageScore));

		double result = reviewService.getReviewsAverageScoreByBookId(bookId);

		verify(reviewServiceClient).getReviewsAverageScoreByBookId(bookId);
		assertEquals(averageScore, result);
	}

	@Test
	void testGetBooksByOrderStatusCompletionAndUserId() {
		List<GetBookTitleResponse> bookTitleResponses = List.of(new GetBookTitleResponse(1L, "Book Title"));
		when(reviewServiceClient.getBooksByOrderStatusCompletionAndUserId()).thenReturn(
			ResponseEntity.ok(bookTitleResponses));

		List<GetBookTitleResponse> result = reviewService.getBooksByOrderStatusCompletionAndUserId();

		verify(reviewServiceClient).getBooksByOrderStatusCompletionAndUserId();
		assertEquals(bookTitleResponses, result);
	}
}