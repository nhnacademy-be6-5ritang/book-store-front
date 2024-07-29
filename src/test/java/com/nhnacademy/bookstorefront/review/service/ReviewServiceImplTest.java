package com.nhnacademy.bookstorefront.review.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.point.feignclient.PointServiceClient;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetBookOrderWithoutReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.feignclient.ReviewServiceClient;
import com.nhnacademy.bookstorefront.review.service.impl.ReviewServiceImpl;
import com.nhnacademy.bookstorefront.upload.feignclient.UploadServiceClient;

class ReviewServiceImplTest {

	@Mock
	private ReviewServiceClient reviewServiceClient;

	@Mock
	private UploadServiceClient uploadServiceClient;

	@Mock
	private PointServiceClient pointServiceClient;

	@InjectMocks
	private ReviewServiceImpl reviewService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetReviews() {
		Pageable pageable = PageRequest.of(0, 5);
		GetReviewResponse review = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, "image-url");
		Page<GetReviewResponse> page = new PageImpl<>(Collections.singletonList(review), pageable, 1);

		when(reviewServiceClient.getReviews(pageable)).thenReturn(ResponseEntity.ok(page));

		Page<GetReviewResponse> result = reviewService.getReviews(pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(review, result.getContent().get(0));
	}

	@Test
	void testGetPhotoReviews() {
		Pageable pageable = PageRequest.of(0, 5);
		GetReviewResponse review = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, "image-url");
		Page<GetReviewResponse> page = new PageImpl<>(Collections.singletonList(review), pageable, 1);

		when(reviewServiceClient.getPhotoReviews(pageable)).thenReturn(ResponseEntity.ok(page));

		Page<GetReviewResponse> result = reviewService.getPhotoReviews(pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(review, result.getContent().get(0));
	}

	@Test
	void testGetGeneralReviews() {
		Pageable pageable = PageRequest.of(0, 5);
		GetReviewResponse review = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, null);
		Page<GetReviewResponse> page = new PageImpl<>(Collections.singletonList(review), pageable, 1);

		when(reviewServiceClient.getGeneralReviews(pageable)).thenReturn(ResponseEntity.ok(page));

		Page<GetReviewResponse> result = reviewService.getGeneralReviews(pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(review, result.getContent().get(0));
	}

	@Test
	void testGetReviewsByBookId() {
		Pageable pageable = PageRequest.of(0, 5);
		GetReviewResponse review = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, null);
		Page<GetReviewResponse> page = new PageImpl<>(Collections.singletonList(review), pageable, 1);

		when(reviewServiceClient.getReviewsByBookId(pageable, 1L)).thenReturn(ResponseEntity.ok(page));

		Page<GetReviewResponse> result = reviewService.getReviewsByBookId(pageable, 1L);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(review, result.getContent().get(0));
	}

	@Test
	void testGetGeneralReviewsByBookId() {
		Pageable pageable = PageRequest.of(0, 5);
		GetReviewResponse review = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, null);
		Page<GetReviewResponse> page = new PageImpl<>(Collections.singletonList(review), pageable, 1);

		when(reviewServiceClient.getGeneralReviewsByBookId(pageable, 1L)).thenReturn(ResponseEntity.ok(page));

		Page<GetReviewResponse> result = reviewService.getGeneralReviewsByBookId(pageable, 1L);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(review, result.getContent().get(0));
	}

	@Test
	void testGetPhotoReviewsByBookId() {
		Pageable pageable = PageRequest.of(0, 5);
		GetReviewResponse review = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, "image-url");
		Page<GetReviewResponse> page = new PageImpl<>(Collections.singletonList(review), pageable, 1);

		when(reviewServiceClient.getPhotoReviewsByBookId(pageable, 1L)).thenReturn(ResponseEntity.ok(page));

		Page<GetReviewResponse> result = reviewService.getPhotoReviewsByBookId(pageable, 1L);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(review, result.getContent().get(0));
	}

	@Test
	void testGetReviewsByUserId() {
		Pageable pageable = PageRequest.of(0, 5);
		GetReviewResponse review = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, null);
		Page<GetReviewResponse> page = new PageImpl<>(Collections.singletonList(review), pageable, 1);

		when(reviewServiceClient.getReviewsByUserId(pageable)).thenReturn(ResponseEntity.ok(page));

		Page<GetReviewResponse> result = reviewService.getReviewsByUserId(pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(review, result.getContent().get(0));
	}

	@Test
	void testGetGeneralReviewsByUserId() {
		Pageable pageable = PageRequest.of(0, 5);
		GetReviewResponse review = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, null);
		Page<GetReviewResponse> page = new PageImpl<>(Collections.singletonList(review), pageable, 1);

		when(reviewServiceClient.getGeneralReviewsByUserId(pageable)).thenReturn(ResponseEntity.ok(page));

		Page<GetReviewResponse> result = reviewService.getGeneralReviewsByUserId(pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(review, result.getContent().get(0));
	}

	@Test
	void testGetPhotoReviewsByUserId() {
		Pageable pageable = PageRequest.of(0, 5);
		GetReviewResponse review = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, "image-url");
		Page<GetReviewResponse> page = new PageImpl<>(Collections.singletonList(review), pageable, 1);

		when(reviewServiceClient.getPhotoReviewsByUserId(pageable)).thenReturn(ResponseEntity.ok(page));

		Page<GetReviewResponse> result = reviewService.getPhotoReviewsByUserId(pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(review, result.getContent().get(0));
	}

	@Test
	void testCreateReview_withFile() {
		CreateReviewRequest request = new CreateReviewRequest(1L, 5, "Great book!", null);
		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(false);
		when(uploadServiceClient.upload(file)).thenReturn(ResponseEntity.ok("file-name"));
		doAnswer(invocation -> null).when(reviewServiceClient).createReview(any(CreateReviewRequest.class));
		doAnswer(invocation -> null).when(pointServiceClient).reviewPointTransaction("PHOTO_REVIEW");

		reviewService.createReview(request, file);

		verify(uploadServiceClient).upload(file);
		verify(reviewServiceClient).createReview(any(CreateReviewRequest.class));
		verify(pointServiceClient).reviewPointTransaction("PHOTO_REVIEW");
	}

	@Test
	void testCreateReview_withoutFile() {
		CreateReviewRequest request = new CreateReviewRequest(1L, 5, "Great book!", null);
		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(true);
		doAnswer(invocation -> null).when(reviewServiceClient).createReview(any(CreateReviewRequest.class));
		doAnswer(invocation -> null).when(pointServiceClient).reviewPointTransaction("REVIEW");

		reviewService.createReview(request, file);

		verify(uploadServiceClient, never()).upload(file);
		verify(reviewServiceClient).createReview(any(CreateReviewRequest.class));
		verify(pointServiceClient).reviewPointTransaction("REVIEW");
	}

	@Test
	void testGetReview() {
		Long reviewId = 1L;
		GetReviewResponse expectedResponse = new GetReviewResponse(1L, 1L, "User Name", 5, "Great book!", null, null);

		when(reviewServiceClient.getReview(reviewId)).thenReturn(ResponseEntity.ok(expectedResponse));

		GetReviewResponse actualResponse = reviewService.getReview(reviewId);

		assertNotNull(actualResponse);
		assertEquals(expectedResponse, actualResponse);
		verify(reviewServiceClient, times(1)).getReview(reviewId);
	}

	@Test
	void testUpdateReview_withFile() {
		UpdateReviewRequest request = new UpdateReviewRequest(5, "Great book!", null);
		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(false);
		when(uploadServiceClient.upload(file)).thenReturn(ResponseEntity.ok("file-name"));
		doAnswer(invocation -> null).when(reviewServiceClient).updateReview(any(UpdateReviewRequest.class), anyLong());

		reviewService.updateReview(request, 1L, file);

		verify(uploadServiceClient).upload(file);
		verify(reviewServiceClient).updateReview(any(UpdateReviewRequest.class), anyLong());
	}

	@Test
	void testUpdateReview_withoutFile() {
		UpdateReviewRequest request = new UpdateReviewRequest(5, "Great book!", null);
		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(true);
		doAnswer(invocation -> null).when(reviewServiceClient).updateReview(any(UpdateReviewRequest.class), anyLong());

		reviewService.updateReview(request, 1L, file);

		verify(uploadServiceClient, never()).upload(file);
		verify(reviewServiceClient).updateReview(any(UpdateReviewRequest.class), anyLong());
	}

	@Test
	void testDeleteReview() {
		doAnswer(invocation -> null).when(reviewServiceClient).deleteReview(anyLong());

		reviewService.deleteReview(1L);

		verify(reviewServiceClient).deleteReview(1L);
	}

	@Test
	void testGetReviewsAverageScoreByBookId() {
		when(reviewServiceClient.getReviewsAverageScoreByBookId(anyLong())).thenReturn(ResponseEntity.ok(4.5));

		double result = reviewService.getReviewsAverageScoreByBookId(1L);

		assertEquals(4.5, result);
	}

	@Test
	void testGetBooksWithoutReviews() {
		GetBookOrderWithoutReviewResponse book = new GetBookOrderWithoutReviewResponse(1L, "Book Title");
		List<GetBookOrderWithoutReviewResponse> books = Collections.singletonList(book);

		when(reviewServiceClient.getBooksWithoutReviews()).thenReturn(ResponseEntity.ok(books));

		List<GetBookOrderWithoutReviewResponse> result = reviewService.getBooksWithoutReviews();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(book, result.get(0));
	}
}