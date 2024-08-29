package com.nhnacademy.bookstorefront.book.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstorefront.bookstatus.service.impl.BookStatusServiceImpl;
import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;
import com.nhnacademy.bookstorefront.category.service.impl.CategoryServiceImpl;
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
				new BookController(bookService, categoryService, bookStatusService, tagService, reviewService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
	}

	@Test
	void testCreateBookForm() throws Exception {
		when(bookStatusService.getBookStatuses()).thenReturn(List.of());
		when(categoryService.getCategories()).thenReturn(List.of());
		when(tagService.getTags()).thenReturn(List.of());

		mockMvc.perform(get("/books/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("book/create-book"))
			.andExpect(model().attribute("bookStatuses", List.of()))
			.andExpect(model().attribute("categories", List.of()))
			.andExpect(model().attribute("tags", List.of()));
	}

	// @Test
	// void testMainPage() throws Exception {
	// 	when(cacheService.getBestSellerBooks()).thenReturn(List.of());
	// 	when(cacheService.getLikesBooks()).thenReturn(List.of());
	// 	when(cacheService.getNewestBooks()).thenReturn(List.of());
	//
	// 	mockMvc.perform(get("/main"))
	// 		.andExpect(status().isOk())
	// 		.andExpect(view().name("index"))
	// 		.andExpect(model().attribute("bestSellerBooksCache", List.of()))
	// 		.andExpect(model().attribute("likesBooksCache", List.of()))
	// 		.andExpect(model().attribute("newestBooksCache", List.of()));
	// }

	@Test
	void testFetchAndSaveBook() throws Exception {
		String isbn = "1234567890";

		mockMvc.perform(post("/books/fetch")
				.param("isbn", isbn))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/books"));

		verify(bookService).saveBookByIsbn(isbn);
	}

	// @Test
	// void testCreateBook() throws Exception {
	// 	CreateBookRequest request = new CreateBookRequest(
	// 		"1234567890", List.of(1L), List.of(2L), "Title", "Author", "Publisher",
	// 		new Date(), "Status", "Description", 10, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, null);
	//
	// 	MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);
	//
	// 	doNothing().when(bookService).createBook(any(CreateBookRequest.class), any(MultipartFile.class));
	//
	// 	mockMvc.perform(multipart("books")
	// 			.file(file)
	// 			.flashAttr("createBookRequest", request))
	// 		.andExpect(status().is3xxRedirection())
	// 		.andExpect(redirectedUrl("/books"));
	// }

	@Test
	void testUpdateBook() throws Exception {
		UpdateBookRequest request = new UpdateBookRequest(
			"1234567890", List.of(1L), List.of(2L), "Title", "Author", "Publisher",
			new Date(), "Status", "Description", 10, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, null);

		MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);

		doNothing().when(bookService).updateBookById(anyLong(), any(UpdateBookRequest.class), any(MultipartFile.class));

		mockMvc.perform(multipart("/books/1")
				.file(file)
				.flashAttr("updateBookRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/books/1"));
	}

	@Test
	void testDeleteBook() throws Exception {
		Long bookId = 1L;

		mockMvc.perform(delete("/books/{bookId}", bookId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/books"));

		verify(bookService).deleteBook(bookId);
	}

	@Test
	void testSearchBooks() throws Exception {
		List<BookSearchResult> mockResults = List.of(
			new BookSearchResult(1L, "Book Title 1"),
			new BookSearchResult(2L, "Book Title 2")
		);

		when(bookService.searchBooks(anyString())).thenReturn(mockResults);

		mockMvc.perform(get("/books/search")
				.param("key", "test search"))
			.andExpect(status().isOk())
			.andExpect(content().json(
				new ObjectMapper().writeValueAsString(mockResults)
			));
	}
}
