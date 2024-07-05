package com.nhnacademy.bookstorefront.bookcart.dto.response;

import lombok.Builder;

@Builder
public record UpdateBookCartResponse(Long bookCartId, int bookQuantity, Long bookId, Long cartId) {
}
