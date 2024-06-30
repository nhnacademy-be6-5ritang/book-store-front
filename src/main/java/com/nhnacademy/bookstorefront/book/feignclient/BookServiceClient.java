package com.nhnacademy.bookstorefront.book.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookListResponse;
import com.nhnacademy.bookstorefront.book.dto.response.CreateBookResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;

@FeignClient(name = "book-feign-client", url = "http://localhost:8083")
public interface BookServiceClient {

	@GetMapping("/books")
	List<BookListResponse> findAllBooks();

	@GetMapping("/books/{bookId}")
	ResponseEntity<GetBookDetailResponse> getBook(@PathVariable Long bookId);

	@GetMapping("/books/details/{isbn}")
	GetBookDetailResponse findBookByIsbn(@PathVariable String isbn);

	@PostMapping("/books")
	public ResponseEntity<CreateBookResponse> createBook(@RequestBody CreateBookRequest request);

	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long bookId);
}
