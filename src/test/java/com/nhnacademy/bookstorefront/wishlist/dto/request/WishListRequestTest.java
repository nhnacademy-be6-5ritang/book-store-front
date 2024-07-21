package com.nhnacademy.bookstorefront.wishlist.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WishListRequestTest {

	@Test
	void testCreateWishListRequest() {
		Long expectedBookId = 1L;

		CreateWishListRequest request = new CreateWishListRequest(expectedBookId);

		assertEquals(expectedBookId, request.bookId());
	}
}
