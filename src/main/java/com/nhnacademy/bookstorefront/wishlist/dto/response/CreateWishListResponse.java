package com.nhnacademy.bookstorefront.wishlist.dto.response;

import lombok.Builder;

@Builder
public record CreateWishListResponse(long bookId, long userId) {
}
