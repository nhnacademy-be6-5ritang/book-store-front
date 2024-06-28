package com.nhnacademy.bookstorefront.book.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhnacademy.bookstorefront.book.dto.response.BookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.BookListResponse;

@FeignClient(name = "book-feign-client", url = "http://localhost:8083")
public interface BookServiceClient {

	@GetMapping("/books")
	List<BookListResponse> findAllBooks();

	@GetMapping("/books/{bookId}")
	BookDetailResponse getBook(@PathVariable Long bookId);

	@GetMapping("/books/details/{isbn}")
	BookDetailResponse findBookByIsbn(@PathVariable String isbn);

}
