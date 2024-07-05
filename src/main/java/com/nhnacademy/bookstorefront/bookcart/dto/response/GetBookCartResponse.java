package com.nhnacademy.bookstorefront.bookcart.dto.response;

import lombok.Builder;

@Builder
public record GetBookCartResponse(Long bookId, Long cartId, int bookQuantity) {
}
