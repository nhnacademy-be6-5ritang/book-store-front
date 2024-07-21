package com.nhnacademy.bookstorefront.bookcart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.feignclient.BookCartServiceClient;
import com.nhnacademy.bookstorefront.bookcart.service.impl.BookCartServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

class BookCartServiceImplTest {

	private BookCartServiceImpl bookCartService;

	@Mock
	private BookCartServiceClient bookCartServiceClient;

	@Mock
	private HttpServletResponse httpServletResponse;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		bookCartService = new BookCartServiceImpl(bookCartServiceClient);
	}

	@Test
	void testGetBookCartsByCartId() {
		String cartId = "testCartId";
		List<GetBookCartResponse> expectedResponses = List.of(
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

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, "testCookie=testValue; Path=/; HttpOnly; Max-Age=3600");
		when(bookCartServiceClient.getBookCartsByCartId(cartId))
			.thenReturn(ResponseEntity.ok().headers(headers).body(expectedResponses));

		List<GetBookCartResponse> actualResponses = bookCartService.getBookCartsByCartId(cartId, httpServletResponse);

		assertEquals(expectedResponses, actualResponses);

		verify(bookCartServiceClient).getBookCartsByCartId(cartId);

		verify(httpServletResponse).addCookie(argThat(cookie -> "testCookie".equals(cookie.getName()) &&
			"testValue".equals(cookie.getValue()) &&
			"/".equals(cookie.getPath()) &&
			cookie.isHttpOnly() &&
			cookie.getMaxAge() == 3600));
	}

	@Test
	void testCreateBookCart() {
		CreateBookCartRequest request = new CreateBookCartRequest(1L, 2);

		when(bookCartServiceClient.createBookCart(request, "testCartId"))
			.thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> response = bookCartService.createBookCart(request, "testCartId");

		assertEquals(ResponseEntity.ok().build(), response);
		verify(bookCartServiceClient).createBookCart(request, "testCartId");
	}

	@Test
	void testUpdateBookCart() {
		Long bookId = 1L;
		UpdateBookCartRequest request = new UpdateBookCartRequest(3);

		when(bookCartServiceClient.updateBookCart(bookId, request, "testCartId"))
			.thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> response = bookCartService.updateBookCart(bookId, request, "testCartId");

		assertEquals(ResponseEntity.ok().build(), response);
		verify(bookCartServiceClient).updateBookCart(bookId, request, "testCartId");
	}

	@Test
	void testDeleteBookCart() {
		Long bookId = 1L;

		when(bookCartServiceClient.deleteBookCart(bookId, "testCartId"))
			.thenReturn(ResponseEntity.ok().build());

		ResponseEntity<Void> response = bookCartService.deleteBookCart(bookId, "testCartId");

		assertEquals(ResponseEntity.ok().build(), response);
		verify(bookCartServiceClient).deleteBookCart(bookId, "testCartId");
	}
}
