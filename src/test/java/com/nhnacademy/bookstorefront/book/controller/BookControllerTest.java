package com.nhnacademy.bookstorefront.book.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstorefront.bookstatus.service.impl.BookStatusServiceImpl;
import com.nhnacademy.bookstorefront.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.bookstorefront.global.config.CacheConfig;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;
import com.nhnacademy.bookstorefront.tag.service.impl.TagServiceImpl;

class BookControllerTest {

	private MockMvc mockMvc;

	@Mock
	private BookServiceImpl bookService;

	@Mock
	private CategoryServiceImpl categoryService;

	@Mock
	private BookStatusServiceImpl bookStatusService;

	@Mock
	private TagServiceImpl tagService;

	@Mock
	private ReviewService reviewService;

	@Mock
	private CacheConfig cacheConfig;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(
				new BookController(bookService, categoryService, bookStatusService, tagService, reviewService, cacheConfig))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
	}

	@Test
	void testCreateBookForm() throws Exception {
		when(bookStatusService.getBookStatuses()).thenReturn(List.of());
		when(categoryService.getCategories()).thenReturn(List.of());
		when(tagService.getTags()).thenReturn(List.of());

		mockMvc.perform(get("/api/books/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("book/create-book"))
			.andExpect(model().attribute("bookStatuses", List.of()))
			.andExpect(model().attribute("categories", List.of()))
			.andExpect(model().attribute("tags", List.of()));
	}

	@Test
	void testUpdateBookForm() throws Exception {
		Long bookId = 1L;
		GetBookDetailResponse bookDetail = new GetBookDetailResponse(
			bookId, "Author", "Publisher", "Status", "Title", "Description", 10,
			new Date(), "ISBN", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, "URL"
		);
		when(bookService.getBook(bookId)).thenReturn(bookDetail);
		when(bookStatusService.getBookStatuses()).thenReturn(List.of());
		when(categoryService.getCategories()).thenReturn(List.of());
		when(tagService.getTags()).thenReturn(List.of());
		when(categoryService.getCategoriesByBookId(bookId)).thenReturn(List.of());
		when(tagService.getTagsByBookId(bookId)).thenReturn(List.of());

		mockMvc.perform(get("/api/books/update/{bookId}", bookId))
			.andExpect(status().isOk())
			.andExpect(view().name("book/update-book"))
			.andExpect(model().attribute("book", bookDetail))
			.andExpect(model().attribute("bookCategories", List.of()))
			.andExpect(model().attribute("bookTags", List.of()))
			.andExpect(model().attribute("bookStatuses", List.of()))
			.andExpect(model().attribute("categories", List.of()))
			.andExpect(model().attribute("tags", List.of()));
	}

	@Test
	void testMainPage() throws Exception {
		when(cacheConfig.getOrderedBooks()).thenReturn(List.of());
		when(cacheConfig.getLikesBooks()).thenReturn(List.of());
		when(cacheConfig.getNewestBooks()).thenReturn(List.of());

		mockMvc.perform(get("/api/books/main"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(model().attribute("orderedBooksCache", List.of()))
			.andExpect(model().attribute("likesBooksCache", List.of()))
			.andExpect(model().attribute("newestBooksCache", List.of()));
	}

	@Test
	void testFindAllBooks() throws Exception {
		Pageable pageable = Pageable.ofSize(10);
		Page<GetBookDetailResponse> bookPage = new PageImpl<>(List.of(new GetBookDetailResponse(
			1L, "Author", "Publisher", "Status", "Title", "Description", 10,
			new Date(), "ISBN", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, "URL"
		)));
		when(bookService.getNewestBooks(any(Pageable.class))).thenReturn(bookPage);

		mockMvc.perform(get("/api/books/page?page=1&size=10"))
			.andExpect(status().isOk())
			.andExpect(view().name("book/list-book"))
			.andExpect(model().attribute("books", bookPage));
	}

	@Test
	void testFindAllBooksByCategoryName() throws Exception {
		Pageable pageable = Pageable.ofSize(10);
		Page<GetBookDetailResponse> bookPage = new PageImpl<>(List.of(new GetBookDetailResponse(
			1L, "Author", "Publisher", "Status", "Title", "Description", 10,
			new Date(), "ISBN", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, "URL"
		)));
		when(bookService.findAllBooksByCategory(any(Pageable.class), anyString())).thenReturn(bookPage);

		mockMvc.perform(get("/api/books/page/category?categoryName=Fiction&page=1&size=10"))
			.andExpect(status().isOk())
			.andExpect(view().name("book/list-book-by-category"))
			.andExpect(model().attribute("books", bookPage));
	}

	@Test
	void testGetBook_Default() throws Exception {
		Long bookId = 1L;
		GetBookDetailResponse bookDetail = new GetBookDetailResponse(
			bookId, "Author", "Publisher", "Status", "Title", "Description", 10,
			new Date(), "ISBN", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, "URL"
		);

		Page<GetReviewResponse> reviews = new PageImpl<>(List.of());

		when(bookService.getBook(bookId)).thenReturn(bookDetail);
		when(categoryService.getCategoriesByBookId(bookId)).thenReturn(List.of());
		when(tagService.getTagsByBookId(bookId)).thenReturn(List.of());
		when(reviewService.getReviewsByBookId(any(Pageable.class), eq(bookId))).thenReturn(reviews);
		when(reviewService.getReviewsAverageScoreByBookId(bookId)).thenReturn(4.5);

		mockMvc.perform(get("/api/books/{bookId}", bookId))
			.andExpect(status().isOk())
			.andExpect(view().name("book/get-book"))
			.andExpect(model().attribute("book", bookDetail))
			.andExpect(model().attribute("bookCategories", List.of()))
			.andExpect(model().attribute("bookTags", List.of()))
			.andExpect(model().attribute("reviews", reviews))
			.andExpect(model().attribute("reviewsAverageScore", 4.5))
			.andExpect(model().attribute("reviewType", "전체"));
	}

	@Test
	void testGetBook_GeneralReviews() throws Exception {
		Long bookId = 1L;
		GetBookDetailResponse bookDetail = new GetBookDetailResponse(
			bookId, "Author", "Publisher", "Status", "Title", "Description", 10,
			new Date(), "ISBN", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, "URL"
		);

		Page<GetReviewResponse> reviews = new PageImpl<>(List.of(
			new GetReviewResponse(bookId, "User1", 5, "Great book!", LocalDateTime.now(),
				"http://example.com/image1.jpg"),
			new GetReviewResponse(bookId, "User2", 4, "Good read.", LocalDateTime.now(),
				"http://example.com/image2.jpg")
		));

		when(bookService.getBook(bookId)).thenReturn(bookDetail);
		when(categoryService.getCategoriesByBookId(bookId)).thenReturn(List.of());
		when(tagService.getTagsByBookId(bookId)).thenReturn(List.of());
		when(reviewService.getGeneralReviewsByBookId(any(Pageable.class), eq(bookId))).thenReturn(reviews);
		when(reviewService.getReviewsAverageScoreByBookId(bookId)).thenReturn(4.5);

		mockMvc.perform(get("/api/books/{bookId}", bookId)
				.param("reviewType", "일반"))
			.andExpect(status().isOk())
			.andExpect(view().name("book/get-book"))
			.andExpect(model().attribute("book", bookDetail))
			.andExpect(model().attribute("bookCategories", List.of()))
			.andExpect(model().attribute("bookTags", List.of()))
			.andExpect(model().attribute("reviews", reviews))
			.andExpect(model().attribute("reviewsAverageScore", 4.5))
			.andExpect(model().attribute("reviewType", "일반"))
			.andExpect(model().attribute("startPage", 1))
			.andExpect(model().attribute("endPage", 1));
	}

	@Test
	void testGetBook_PhotoReviews() throws Exception {
		Long bookId = 1L;
		GetBookDetailResponse bookDetail = new GetBookDetailResponse(
			bookId, "Author", "Publisher", "Status", "Title", "Description", 10,
			new Date(), "ISBN", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, "URL"
		);

		Page<GetReviewResponse> reviews = new PageImpl<>(List.of(
			new GetReviewResponse(bookId, "User1", 5, "Photo review 1", LocalDateTime.now(),
				"http://example.com/photo1.jpg"),
			new GetReviewResponse(bookId, "User2", 4, "Photo review 2", LocalDateTime.now(),
				"http://example.com/photo2.jpg")
		));

		when(bookService.getBook(bookId)).thenReturn(bookDetail);
		when(categoryService.getCategoriesByBookId(bookId)).thenReturn(List.of());
		when(tagService.getTagsByBookId(bookId)).thenReturn(List.of());
		when(reviewService.getPhotoReviewsByBookId(any(Pageable.class), eq(bookId))).thenReturn(reviews);
		when(reviewService.getReviewsAverageScoreByBookId(bookId)).thenReturn(4.5);

		mockMvc.perform(get("/api/books/{bookId}", bookId)
				.param("reviewType", "사진"))
			.andExpect(status().isOk())
			.andExpect(view().name("book/get-book"))
			.andExpect(model().attribute("book", bookDetail))
			.andExpect(model().attribute("bookCategories", List.of()))
			.andExpect(model().attribute("bookTags", List.of()))
			.andExpect(model().attribute("reviews", reviews))
			.andExpect(model().attribute("reviewsAverageScore", 4.5))
			.andExpect(model().attribute("reviewType", "사진"))
			.andExpect(model().attribute("startPage", 1))
			.andExpect(model().attribute("endPage", 1));
	}

	@Test
	void testGetBookDetail() throws Exception {
		Long bookId = 1L;
		GetBookDetailResponse bookDetail = new GetBookDetailResponse(
			bookId, "Author", "Publisher", "Status", "Title", "Description", 10,
			new Date(), "ISBN", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, "URL"
		);
		when(bookService.getBook(bookId)).thenReturn(bookDetail);
		when(categoryService.getCategoriesByBookId(bookId)).thenReturn(List.of());
		when(tagService.getTagsByBookId(bookId)).thenReturn(List.of());

		mockMvc.perform(get("/api/books/detail/{bookId}", bookId))
			.andExpect(status().isOk())
			.andExpect(view().name("book/get-book-detail"))
			.andExpect(model().attribute("book", bookDetail))
			.andExpect(model().attribute("bookCategories", List.of()))
			.andExpect(model().attribute("bookTags", List.of()));
	}

	@Test
	void testFetchAndSaveBook() throws Exception {
		String isbn = "1234567890";

		mockMvc.perform(post("/api/books/fetch")
				.param("isbn", isbn))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/books/page"));

		verify(bookService).saveBookByIsbn(isbn);
	}

	@Test
	void testCreateBook() throws Exception {
		mockMvc.perform(post("/api/books")
				.param("bookIsbn", "1234567890")
				.param("categories", "1")
				.param("tags", "1")
				.param("bookTitle", "Title")
				.param("authorName", "Author")
				.param("publisherName", "Publisher")
				.param("bookPublishDate", "2023-07-21")
				.param("bookStatusName", "Status")
				.param("bookDescription", "Description")
				.param("bookQuantity", "10")
				.param("bookPrice", "10.00")
				.param("bookSalePrice", "1.00")
				.param("bookSalePercent", "0.00"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/books/page"));

		verify(bookService).createBook(any(CreateBookRequest.class));
	}

	@Test
	void testUpdateBook() throws Exception {
		Long bookId = 1L;

		mockMvc.perform(put("/api/books/{bookId}", bookId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("bookIsbn", "1234567890")
				.param("categories", "1")
				.param("tags", "1")
				.param("bookTitle", "Title")
				.param("authorName", "Author")
				.param("publisherName", "Publisher")
				.param("bookPublishDate", "2023-07-21")
				.param("bookStatusName", "Status")
				.param("bookDescription", "Description")
				.param("bookQuantity", "10")
				.param("bookPrice", "10.00")
				.param("bookSalePrice", "1.00")
				.param("bookSalePercent", "0.00"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/books/detail/" + bookId));

		verify(bookService).updateBookById(eq(bookId), any(UpdateBookRequest.class));
	}

	@Test
	void testDeleteBook() throws Exception {
		Long bookId = 1L;

		mockMvc.perform(delete("/api/books/{bookId}", bookId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/books/page"));

		verify(bookService).deleteBook(bookId);
	}

	@Test
	void testSearchBooks() throws Exception {
		List<BookSearchResult> mockResults = List.of(
			new BookSearchResult(1L, "Book Title 1"),
			new BookSearchResult(2L, "Book Title 2")
		);

		when(bookService.searchBooks(anyString())).thenReturn(mockResults);

		mockMvc.perform(get("/api/books/search/test")
				.param("key", "test search"))
			.andExpect(status().isOk())
			.andExpect(content().json(
				new ObjectMapper().writeValueAsString(mockResults)
			));
	}
}
