package com.nhnacademy.bookstorefront.search.feignclient;

import com.nhnacademy.bookstorefront.search.dto.BookSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "searchService", url = "http://localhost:8090/api/search")
public interface SearchServiceClient {

	@GetMapping("/books")
	Page<BookSearchResponse> searchBooks(@RequestParam String query, Pageable pageable);

	@GetMapping("/authors")
	Page<BookSearchResponse> searchAuthors(@RequestParam String query, Pageable pageable);

	@GetMapping("/publisher")
	Page<BookSearchResponse> searchPublishers(@RequestParam String query, Pageable pageable);

	@GetMapping("/tag")
	Page<BookSearchResponse> searchBooksByTag(@RequestParam String query, Pageable pageable);
}