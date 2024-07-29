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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstorefront.bookstatus.service.impl.BookStatusServiceImpl;
import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;
import com.nhnacademy.bookstorefront.category.service.impl.CategoryServiceImpl;
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
	private CacheServiceImpl cacheService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(
				new BookController(bookService, categoryService, bookStatusService, tagService, reviewService,
					cacheService))
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
		when(cacheService.getOrderedBooks()).thenReturn(List.of());
		when(cacheService.getLikesBooks()).thenReturn(List.of());
		when(cacheService.getNewestBooks()).thenReturn(List.of());

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
			new GetReviewResponse(1L, bookId, "User1", 5, "Great book!", LocalDateTime.now(),
				"http://example.com/image1.jpg"),
			new GetReviewResponse(2L, bookId, "User2", 4, "Good read.", LocalDateTime.now(),
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
			new GetReviewResponse(1L, bookId, "User1", 5, "Great book!", LocalDateTime.now(),
				"http://example.com/image1.jpg"),
			new GetReviewResponse(2L, bookId, "User2", 4, "Good read.", LocalDateTime.now(),
				"http://example.com/image2.jpg")
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
		CreateBookRequest request = new CreateBookRequest(
			"1234567890", List.of(1L), List.of(2L), "Title", "Author", "Publisher",
			new Date(), "Status", "Description", 10, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, null);

		MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);

		doNothing().when(bookService).createBook(any(CreateBookRequest.class), any(MultipartFile.class));

		mockMvc.perform(multipart("/api/books")
				.file(file)
				.flashAttr("createBookRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/books/page"));
	}

	@Test
	void testUpdateBook() throws Exception {
		UpdateBookRequest request = new UpdateBookRequest(
			"1234567890", List.of(1L), List.of(2L), "Title", "Author", "Publisher",
			new Date(), "Status", "Description", 10, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, null);

		MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);

		doNothing().when(bookService).updateBookById(anyLong(), any(UpdateBookRequest.class), any(MultipartFile.class));

		mockMvc.perform(multipart("/api/books/1")
				.file(file)
				.flashAttr("updateBookRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/books/detail/1"));
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
