package com.nhnacademy.bookstorefront.review.service;

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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.book.dto.response.GetBookTitleResponse;
import com.nhnacademy.bookstorefront.point.feignclient.PointServiceClient;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.feignclient.ReviewServiceClient;
import com.nhnacademy.bookstorefront.review.service.impl.ReviewServiceImpl;
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
	void testGetGeneralReviewsByBookId() {
		Long bookId = 1L;
		Pageable pageable = Pageable.ofSize(10);
		List<GetReviewResponse> reviews = List.of(new GetReviewResponse(
			bookId,
			"User1",
			5,
			"Fantastic!",
			LocalDateTime.now(),
			"http://example.com/image.jpg"
		));
		Page<GetReviewResponse> expectedPage = new PageImpl<>(reviews);

		when(reviewServiceClient.getGeneralReviewsByBookId(eq(pageable), eq(bookId)))
			.thenReturn(ResponseEntity.ok(expectedPage));

		Page<GetReviewResponse> actualPage = reviewService.getGeneralReviewsByBookId(pageable, bookId);

		assertEquals(expectedPage, actualPage);
		verify(reviewServiceClient).getGeneralReviewsByBookId(pageable, bookId);
	}

	@Test
	void testGetPhotoReviewsByBookId() {
		Long bookId = 1L;
		Pageable pageable = Pageable.ofSize(10);
		List<GetReviewResponse> reviews = List.of(new GetReviewResponse(
			bookId,
			"User2",
			4,
			"Nice photos!",
			LocalDateTime.now(),
			"http://example.com/photo.jpg"
		));
		Page<GetReviewResponse> expectedPage = new PageImpl<>(reviews);

		when(reviewServiceClient.getPhotoReviewsByBookId(eq(pageable), eq(bookId)))
			.thenReturn(ResponseEntity.ok(expectedPage));

		Page<GetReviewResponse> actualPage = reviewService.getPhotoReviewsByBookId(pageable, bookId);

		assertEquals(expectedPage, actualPage);
		verify(reviewServiceClient).getPhotoReviewsByBookId(pageable, bookId);
	}

	@Test
	void testGetReviewsByUserId() {
		Pageable pageable = Pageable.ofSize(10);
		List<GetReviewResponse> reviews = List.of(new GetReviewResponse(
			1L,
			"User3",
			3,
			"It was okay.",
			LocalDateTime.now(),
			"http://example.com/user_review.jpg"
		));
		Page<GetReviewResponse> expectedPage = new PageImpl<>(reviews);

		when(reviewServiceClient.getReviewsByUserId(eq(pageable)))
			.thenReturn(ResponseEntity.ok(expectedPage));

		Page<GetReviewResponse> actualPage = reviewService.getReviewsByUserId(pageable);

		assertEquals(expectedPage, actualPage);
		verify(reviewServiceClient).getReviewsByUserId(pageable);
	}

	@Test
	void testGetGeneralReviewsByUserId() {
		Pageable pageable = Pageable.ofSize(10);
		List<GetReviewResponse> reviews = List.of(new GetReviewResponse(
			1L,
			"User4",
			5,
			"Excellent experience!",
			LocalDateTime.now(),
			"http://example.com/excellent_review.jpg"
		));
		Page<GetReviewResponse> expectedPage = new PageImpl<>(reviews);

		when(reviewServiceClient.getGeneralReviewsByUserId(eq(pageable)))
			.thenReturn(ResponseEntity.ok(expectedPage));

		Page<GetReviewResponse> actualPage = reviewService.getGeneralReviewsByUserId(pageable);

		assertEquals(expectedPage, actualPage);
		verify(reviewServiceClient).getGeneralReviewsByUserId(pageable);
	}

	@Test
	void testGetPhotoReviewsByUserId() {
		Pageable pageable = Pageable.ofSize(10);
		List<GetReviewResponse> reviews = List.of(new GetReviewResponse(
			1L,
			"User5",
			2,
			"Not satisfied.",
			LocalDateTime.now(),
			"http://example.com/poor_review.jpg"
		));
		Page<GetReviewResponse> expectedPage = new PageImpl<>(reviews);

		when(reviewServiceClient.getPhotoReviewsByUserId(eq(pageable)))
			.thenReturn(ResponseEntity.ok(expectedPage));

		Page<GetReviewResponse> actualPage = reviewService.getPhotoReviewsByUserId(pageable);

		assertEquals(expectedPage, actualPage);
		verify(reviewServiceClient).getPhotoReviewsByUserId(pageable);
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
