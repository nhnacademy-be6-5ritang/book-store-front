package com.nhnacademy.bookstorefront.wishlist.dto.request;

import lombok.Builder;

@Builder
public record GetWishListsRequest(Long userId) {
}
