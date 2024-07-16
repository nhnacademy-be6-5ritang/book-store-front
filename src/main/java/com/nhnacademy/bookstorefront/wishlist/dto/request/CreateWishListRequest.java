package com.nhnacademy.bookstorefront.wishlist.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateWishListRequest(
	@NotNull Long bookId) {
}
