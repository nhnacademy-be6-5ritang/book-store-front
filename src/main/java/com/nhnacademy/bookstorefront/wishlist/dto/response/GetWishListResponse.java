package com.nhnacademy.bookstorefront.wishlist.dto.response;

import lombok.Builder;

@Builder
public record GetWishListResponse(Long bookId) {
}
