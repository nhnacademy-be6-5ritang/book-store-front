package com.nhnacademy.bookstorefront.review.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
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

import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookTitleResponse;
import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;
import com.nhnacademy.bookstorefront.review.dto.request.CreateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.request.UpdateReviewRequest;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private ReviewService reviewService;

	@MockBean
	private BookService bookService;

	@MockBean
	private GlobalDataControllerAdvice globalDataControllerAdvice;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new ReviewController(reviewService, bookService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	void testCreateReviewPage() throws Exception {
		GetBookDetailResponse book = new GetBookDetailResponse(
			1L, "Author Name", "Publisher Name", "Book Status", "Book Title", "Book Description",
			10, new Date(), "ISBN123456789", BigDecimal.valueOf(29.99), BigDecimal.valueOf(19.99),
			BigDecimal.valueOf(33.34), "image-url");

		when(bookService.getBook(anyLong())).thenReturn(book);

		mockMvc.perform(get("/api/reviews/create/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/create-review"))
			.andExpect(model().attribute("book", book));
	}

	@Test
	void testGetReviews() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		Page<GetReviewResponse> reviewPage = new PageImpl<>(
			Collections.singletonList(reviewResponse), PageRequest.of(0, 10), 1);

		when(reviewService.getReviews(any(Pageable.class))).thenReturn(reviewPage);

		mockMvc.perform(get("/api/reviews/page")
				.param("page", "0")
				.param("size", "10")
				.param("sort", "reviewCreatedAt,desc"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/list-all-review"))
			.andExpect(model().attribute("reviews", reviewPage));
	}

	@Test
	void testCreateReview() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "testfile.txt", "text/plain", "test content".getBytes());

		doNothing().when(reviewService).createReview(any(CreateReviewRequest.class), any());

		mockMvc.perform(multipart("/api/reviews")
				.file(file)
				.param("bookId", "1")
				.param("reviewScore", "5")
				.param("reviewComment", "Excellent book!")
				.param("fileName", "testfile.txt"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/users/me/reviews/page"));
	}

	@Test
	void testUpdateReview() throws Exception {
		doNothing().when(reviewService).updateReview(any(UpdateReviewRequest.class), anyLong());

		mockMvc.perform(put("/api/reviews/1")
				.param("reviewScore", "4")
				.param("reviewComment", "Updated review comment"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/users/me/reviews/page"));
	}

	@Test
	void testGetUserReviews() throws Exception {
		GetReviewResponse reviewResponse = new GetReviewResponse(
			1L, "User Name", 5, "Great book!", LocalDateTime.now(), "image-url");

		Page<GetReviewResponse> reviewPage = new PageImpl<>(
			Collections.singletonList(reviewResponse), PageRequest.of(0, 5), 1);

		List<GetBookTitleResponse> bookTitles = Collections.singletonList(new GetBookTitleResponse(1L, "Book Title"));

		when(reviewService.getBooksByOrderStatusCompletionAndUserId()).thenReturn(bookTitles);
		when(reviewService.getReviewsByUserId(any(Pageable.class))).thenReturn(reviewPage);

		mockMvc.perform(get("/api/users/me/reviews/page")
				.param("page", "0")
				.param("size", "5"))
			.andExpect(status().isOk())
			.andExpect(view().name("review/list-by-user-review"))
			.andExpect(model().attribute("reviews", reviewPage))
			.andExpect(model().attribute("possibleBooks", bookTitles));
	}

	@Test
	void testDeleteReview() throws Exception {
		doNothing().when(reviewService).deleteReview(anyLong());

		mockMvc.perform(delete("/api/reviews/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/users/me/reviews/page"));
	}
}