package com.nhnacademy.bookstorefront.bookcart.dto.response;

import lombok.Builder;

@Builder
public record CreateBookCartResponse(Long bookId, int bookQuantity) {
}
