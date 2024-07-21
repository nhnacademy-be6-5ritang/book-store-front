package com.nhnacademy.bookstorefront.wishlist.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.response.GetWishListResponse;
import com.nhnacademy.bookstorefront.wishlist.feignclient.WishListServiceClient;

class WishListServiceImplTest {

	@InjectMocks
	private WishListServiceImpl wishListService;

	@Mock
	private WishListServiceClient wishListServiceClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetWishLists() {
		List<GetWishListResponse> wishListResponses = List.of(
			new GetWishListResponse(
				1L,
				101L,
				"http://example.com/image.jpg",
				"Book Title",
				"Author Name",
				"Publisher Name",
				BigDecimal.valueOf(20.00),
				BigDecimal.valueOf(10.00)
			)
		);
		when(wishListServiceClient.getWishLists()).thenReturn(ResponseEntity.ok(wishListResponses));

		List<GetWishListResponse> result = wishListService.getWishLists();

		verify(wishListServiceClient).getWishLists();
		assertEquals(wishListResponses, result);
	}

	@Test
	void testCreateWishList() {
		CreateWishListRequest request = new CreateWishListRequest(1L);
		when(wishListServiceClient.createWishList(request)).thenReturn(
			ResponseEntity.status(HttpStatus.CREATED).build());

		ResponseEntity<Void> response = wishListService.createWishList(request);

		verify(wishListServiceClient).createWishList(request);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void testDeleteWishList() {
		Long wishListId = 1L;
		when(wishListServiceClient.deleteWishList(wishListId)).thenReturn(ResponseEntity.noContent().build());

		wishListService.deleteWishList(wishListId);

		verify(wishListServiceClient).deleteWishList(wishListId);
	}
}