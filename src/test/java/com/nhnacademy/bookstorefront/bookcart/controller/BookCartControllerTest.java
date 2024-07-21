package com.nhnacademy.bookstorefront.bookcart.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.service.BookCartService;

import jakarta.servlet.http.Cookie;

class BookCartControllerTest {

	private MockMvc mockMvc;

	@Mock
	private BookCartService bookCartService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new BookCartController(bookCartService))
			.build();
	}

	@Test
	void testGetBookCarts() throws Exception {
		String cartId = "testCartId";
		List<GetBookCartResponse> bookCarts = List.of(
			new GetBookCartResponse(
				1L,
				cartId,
				"http://example.com/image.jpg",
				"Book Title",
				"Author Name",
				"Publisher Name",
				BigDecimal.TEN,
				BigDecimal.ONE,
				BigDecimal.ZERO,
				100,
				2
			)
		);

		when(bookCartService.getBookCartsByCartId(eq(cartId), any())).thenReturn(bookCarts);

		mockMvc.perform(get("/api/carts/me")
				.cookie(new Cookie("cartId", cartId)))
			.andExpect(status().isOk())
			.andExpect(view().name("cart/list-cart"))
			.andExpect(model().attribute("bookCarts", bookCarts));

		verify(bookCartService).getBookCartsByCartId(eq(cartId), any());
	}

	@Test
	void testCreateBookCart() throws Exception {
		CreateBookCartRequest request = new CreateBookCartRequest(1L, 2);

		mockMvc.perform(post("/api/carts/me")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"bookId\":1,\"bookQuantity\":2}"))
			.andExpect(status().isOk());

		verify(bookCartService).createBookCart(eq(request), any());
	}

	@Test
	void testUpdateBookCart() throws Exception {
		Long bookId = 1L;
		UpdateBookCartRequest request = new UpdateBookCartRequest(3);

		mockMvc.perform(put("/api/carts/me/{bookId}", bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"bookQuantity\":3}"))
			.andExpect(status().isOk());

		verify(bookCartService).updateBookCart(eq(bookId), eq(request), any());
	}

	@Test
	void testDeleteBookCart() throws Exception {
		Long bookId = 1L;

		mockMvc.perform(delete("/api/carts/me/{bookId}", bookId)
				.cookie(new Cookie("cartId", "testCartId")))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/carts/me"));

		verify(bookCartService).deleteBookCart(eq(bookId), any());
	}
}