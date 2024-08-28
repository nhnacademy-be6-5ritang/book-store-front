package com.nhnacademy.bookstorefront.book.feignclient;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "book-feign-client", url = "http://localhost:8090/api/books")
public interface BookServiceClient {

    @GetMapping
    ResponseEntity<Page<GetBookDetailResponse>> getBooks(Pageable pageable);

    @GetMapping("/{bookId}")
    ResponseEntity<GetBookDetailResponse> getBook(@PathVariable Long bookId);

    @PostMapping("/fetch/book-lists")
    ResponseEntity<String> fetchAndSaveBooks(@RequestParam Long count);

    @PostMapping("/fetch")
    ResponseEntity<Void> saveBookByIsbn(@RequestParam String isbn);

    @PostMapping
    ResponseEntity<Void> createBook(@RequestBody CreateBookRequest request);

    @PostMapping("/{bookId}")
    ResponseEntity<Void> updateBookById(@PathVariable Long bookId,
                                        @RequestBody UpdateBookRequest request);

    @DeleteMapping("/{bookId}")
    ResponseEntity<Void> deleteBook(@PathVariable Long bookId);

    @PutMapping("/{bookId}/{quantity}")
    ResponseEntity<Void> updateQuantity(@PathVariable Long bookId, @PathVariable int quantity);

    @GetMapping("/search")
    ResponseEntity<List<BookSearchResult>> searchBooks(@RequestParam("key") String search);
}
