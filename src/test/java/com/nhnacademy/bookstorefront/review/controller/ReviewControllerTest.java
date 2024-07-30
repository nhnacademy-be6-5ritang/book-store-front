package com.nhnacademy.bookstorefront.review.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.service.BookOrderService;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetBookOrderWithoutReviewResponse;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private ReviewService reviewService;

	@MockBean
	private BookOrderService bookOrderService;

	@MockBean
	private GlobalDataControllerAdvice globalDataControllerAdvice;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new ReviewController(reviewService, bookOrderService))
			.setControllerAdvice(globalDataControllerAdvice)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	void testCreateReviewPage() throws Exception {
		GetBookOrderGetBookResponse bookResponse = new GetBookOrderGetBookResponse(
			"image-url", "Book Title", BigDecimal.valueOf(29.99), "Book Description", 1L);
		GetBookOrderResponse orderBook = new GetBookOrderResponse(bookResponse, 1, 1L, 1L);

		when(bookOrderService.getBookOrder(anyLong())).thenReturn(orderBook);

		mockMvc.perform(get("/api/reviews/create/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/create-review"))
			.andExpect(model().attribute("orderBook", orderBook))
			.andExpect(model().attribute("book", bookResponse));
	}

	@Test
	void testUpdateReviewPage() throws Exception {
		GetReviewResponse review = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		when(reviewService.getReview(anyLong())).thenReturn(review);

		mockMvc.perform(get("/api/reviews/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/update-review"))
			.andExpect(model().attribute("review", review));
	}

	@Test
	void testGetReviews_All() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		Page<GetReviewResponse> reviewPage = new PageImpl<>(
			Collections.singletonList(reviewResponse), PageRequest.of(0, 5), 1);

		when(reviewService.getReviews(any(Pageable.class))).thenReturn(reviewPage);

		mockMvc.perform(get("/api/reviews/page")
				.param("reviewType", "전체"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/list-all-review"))
			.andExpect(model().attribute("reviews", reviewPage));
	}

	@Test
	void testGetReviews_General() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		Page<GetReviewResponse> reviewPage = new PageImpl<>(
			Collections.singletonList(reviewResponse), PageRequest.of(0, 5), 1);

		when(reviewService.getGeneralReviews(any(Pageable.class))).thenReturn(reviewPage);

		mockMvc.perform(get("/api/reviews/page")
				.param("reviewType", "일반"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/list-all-review"))
			.andExpect(model().attribute("reviews", reviewPage));
	}

	@Test
	void testGetUserReviews_All() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		Page<GetReviewResponse> reviewPage = new PageImpl<>(
			Collections.singletonList(reviewResponse), PageRequest.of(0, 5), 1);

		List<GetBookOrderWithoutReviewResponse> bookTitles = Collections.singletonList(
			GetBookOrderWithoutReviewResponse.builder()
				.orderListId(1L)
				.bookTitle("Book Title")
				.build()
		);

		when(reviewService.getBooksWithoutReviews()).thenReturn(bookTitles);
		when(reviewService.getReviewsByUserId(any(Pageable.class))).thenReturn(reviewPage);

		mockMvc.perform(get("/api/users/me/reviews/page")
				.param("reviewType", "전체"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/list-by-user-review"))
			.andExpect(model().attribute("reviews", reviewPage))
			.andExpect(model().attribute("possibleBooks", bookTitles));
	}

	@Test
	void testGetUserReviews_General() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		Page<GetReviewResponse> reviewPage = new PageImpl<>(
			Collections.singletonList(reviewResponse), PageRequest.of(0, 5), 1);

		List<GetBookOrderWithoutReviewResponse> bookTitles = Collections.singletonList(
			GetBookOrderWithoutReviewResponse.builder()
				.orderListId(1L)
				.bookTitle("Book Title")
				.build()
		);

		when(reviewService.getBooksWithoutReviews()).thenReturn(bookTitles);
		when(reviewService.getGeneralReviewsByUserId(any(Pageable.class))).thenReturn(reviewPage);

		mockMvc.perform(get("/api/users/me/reviews/page")
				.param("reviewType", "일반"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/list-by-user-review"))
			.andExpect(model().attribute("reviews", reviewPage))
			.andExpect(model().attribute("possibleBooks", bookTitles));
	}

	@Test
	void testGetUserReviews_Photo() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		Page<GetReviewResponse> reviewPage = new PageImpl<>(
			Collections.singletonList(reviewResponse), PageRequest.of(0, 5), 1);

		List<GetBookOrderWithoutReviewResponse> bookTitles = Collections.singletonList(
			GetBookOrderWithoutReviewResponse.builder()
				.orderListId(1L)
				.bookTitle("Book Title")
				.build()
		);

		when(reviewService.getBooksWithoutReviews()).thenReturn(bookTitles);
		when(reviewService.getPhotoReviewsByUserId(any(Pageable.class))).thenReturn(reviewPage);

		mockMvc.perform(get("/api/users/me/reviews/page")
				.param("reviewType", "사진"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/list-by-user-review"))
			.andExpect(model().attribute("reviews", reviewPage))
			.andExpect(model().attribute("possibleBooks", bookTitles));
	}

	@Test
	void testGetReviews_Photo() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		Page<GetReviewResponse> reviewPage = new PageImpl<>(
			Collections.singletonList(reviewResponse), PageRequest.of(0, 5), 1);

		when(reviewService.getPhotoReviews(any(Pageable.class))).thenReturn(reviewPage);

		mockMvc.perform(get("/api/reviews/page")
				.param("reviewType", "사진"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/list-all-review"))
			.andExpect(model().attribute("reviews", reviewPage));
	}

	@Test
	void testGetReviewByBook() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		when(reviewService.getReview(anyLong())).thenReturn(reviewResponse);

		mockMvc.perform(get("/api/reviews/book/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/get-review-by-book"))
			.andExpect(model().attribute("review", reviewResponse));
	}

	@Test
	void testGetReviewByUser() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		when(reviewService.getReview(anyLong())).thenReturn(reviewResponse);

		mockMvc.perform(get("/api/reviews/user/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/get-review-by-user"))
			.andExpect(model().attribute("review", reviewResponse));
	}

	@Test
	void testGetReviewByAdmin() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, 1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		when(reviewService.getReview(anyLong())).thenReturn(reviewResponse);

		mockMvc.perform(get("/api/reviews/admin/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/get-review-by-admin"))
			.andExpect(model().attribute("review", reviewResponse));
	}

	@Test
	void testCreateReview() throws Exception {
		CreateReviewRequest request = new CreateReviewRequest(
			1L, 3, "User Name", "image-url");

		MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);

		doNothing().when(reviewService).createReview(any(CreateReviewRequest.class), any(MultipartFile.class));

		mockMvc.perform(multipart("/api/reviews")
				.file(file)
				.flashAttr("createReviewRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/users/me/reviews/page"));
	}

	@Test
	void testUpdateReview() throws Exception {
		UpdateReviewRequest request = new UpdateReviewRequest(
			4, "User Name", "image-url");

		MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);

		doNothing().when(reviewService)
			.updateReview(any(UpdateReviewRequest.class), anyLong(), any(MultipartFile.class));

		mockMvc.perform(multipart("/api/reviews/1")
				.file(file)
				.flashAttr("updateReviewRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/users/me/reviews/page"));
	}

	@Test
	void testDeleteReview() throws Exception {
		doNothing().when(reviewService).deleteReview(anyLong());

		mockMvc.perform(delete("/api/reviews/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/users/admin/reviews/page"));
	}
}
