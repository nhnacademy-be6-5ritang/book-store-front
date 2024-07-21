package com.nhnacademy.bookstorefront.author.feignclient;

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

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;

@FeignClient(name = "author-feign-client", url = "http://localhost:8090/api/authors")
public interface AuthorServiceClient {

	@GetMapping
	ResponseEntity<List<AuthorDto>> getAuthors();

	@GetMapping("/page")
	ResponseEntity<Page<AuthorDto>> getAuthors(Pageable pageable);

	@GetMapping("/{authorId}")
	ResponseEntity<AuthorDto> getAuthor(@PathVariable Long authorId);

	@PostMapping
	ResponseEntity<Void> createAuthor(@RequestBody AuthorDto request);

	@PutMapping("/{authorId}")
	ResponseEntity<Void> updateAuthor(@PathVariable Long authorId, @RequestBody AuthorDto request);

	@DeleteMapping("/{authorId}")
	ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId);
}
