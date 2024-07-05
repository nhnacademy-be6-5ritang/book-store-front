package com.nhnacademy.bookstorefront.bookcart.dto.request;

import lombok.Builder;

@Builder
public record DeleteBookCartRequest(Long bookCartId) {
}
