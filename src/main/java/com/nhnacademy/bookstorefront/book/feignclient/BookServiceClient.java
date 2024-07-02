package com.nhnacademy.bookstorefront.book.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.book.dto.request.BookUpdateRequest;
import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.CreateBookResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.UpdateBookResponse;

@FeignClient(name = "book-feign-client", url = "http://localhost:8083")
public interface BookServiceClient {

	@GetMapping("/books")
	ResponseEntity<Page<GetBookDetailResponse>> findAllBooks(Pageable pageable);

	@GetMapping("/books/{bookId}")
	ResponseEntity<GetBookDetailResponse> getBook(@PathVariable Long bookId);

	@PostMapping("/books")
	ResponseEntity<CreateBookResponse> createBook(@RequestBody CreateBookRequest request);

	@PutMapping("/books/{bookId}")
	UpdateBookResponse updateBookById(@PathVariable Long bookId, @RequestBody UpdateBookRequest request);

	@PatchMapping("/books/{bookId}")
	GetBookDetailResponse updateBook(@PathVariable Long bookId, @RequestBody BookUpdateRequest request);

	@DeleteMapping("/books/{bookId}")
	ResponseEntity<Void> deleteBook(@PathVariable Long bookId);
}
