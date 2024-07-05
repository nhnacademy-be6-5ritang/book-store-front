package com.nhnacademy.bookstorefront.bookcart.dto.request;

import lombok.Builder;

@Builder
public record CreateBookCartRequest(Long bookId, Long cartId, int bookQuantity) {
}
