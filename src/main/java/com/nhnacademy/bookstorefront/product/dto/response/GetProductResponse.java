package com.nhnacademy.bookstorefront.product.dto.response;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 책 상세페이지 응답 DTO
 *
 * @author 김기욱
 * @version 1.0
 */
public record GetProductResponse(
        Long bookId,
        String authorName,
        String publisherName,
        String bookStatusName,
        String bookTitle,
        String bookDescription,
        int bookQuantity,
        @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookPublishDate,
        String bookIsbn,
        BigDecimal bookPrice,
        BigDecimal bookSalePrice,
        BigDecimal bookSalePercent,
        String bookImageUrl) {
}
