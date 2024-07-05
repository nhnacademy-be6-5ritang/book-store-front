package com.nhnacademy.bookstorefront.bookcart.dto.request;

import lombok.Builder;

@Builder
public record UpdateBookCartRequest(int bookQuantity, Long bookId, Long cartId) {
}
