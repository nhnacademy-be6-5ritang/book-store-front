package com.nhnacademy.bookstorefront.book.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.CreateBookResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.dto.response.UpdateBookResponse;

@FeignClient(name = "book-feign-client", url = "http://localhost:8090/api/books")
public interface BookServiceClient {

	@GetMapping
	ResponseEntity<List<GetBookDetailResponse>> findAllBooks();

	@GetMapping("/page")
	ResponseEntity<Page<GetBookDetailResponse>> findAllBooks(Pageable pageable);

	@GetMapping("/page/category")
	ResponseEntity<Page<GetBookDetailResponse>> findAllBooksByCategoryName(Pageable pageable,
		@RequestParam String categoryName);

	@GetMapping("/{bookId}")
	ResponseEntity<GetBookDetailResponse> getBook(@PathVariable Long bookId);

	@PostMapping("/fetch/book-lists")
	ResponseEntity<String> fetchAndSaveBooks(@RequestParam Long count);

	@PostMapping("/fetch")
	ResponseEntity<String> fetchAndSaveBook(@RequestParam String isbn);

	@PostMapping
	ResponseEntity<CreateBookResponse> createBook(@RequestBody CreateBookRequest request);

	@PutMapping("/{bookId}")
	ResponseEntity<UpdateBookResponse> updateBookById(@PathVariable Long bookId,
		@RequestBody UpdateBookRequest request);

	@DeleteMapping("/{bookId}")
	ResponseEntity<Void> deleteBook(@PathVariable Long bookId);

	@PutMapping("/{bookId}/{quantity}")
	ResponseEntity<Void> updateQuantity(@PathVariable Long bookId, @PathVariable int quantity);

	@GetMapping("/search/test")
	ResponseEntity<List<BookSearchResult>> searchBooks(@RequestParam("key") String search);

}
