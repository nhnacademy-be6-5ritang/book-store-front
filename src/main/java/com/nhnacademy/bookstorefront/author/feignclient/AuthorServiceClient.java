package com.nhnacademy.bookstorefront.author.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;

@FeignClient(name = "author-feign-client", url = "http://localhost:8083/api/authors")
public interface AuthorServiceClient {

	@GetMapping
	ResponseEntity<List<AuthorDto>> getAuthors();

	@GetMapping("/{authorId}")
	ResponseEntity<AuthorDto> getAuthor(@PathVariable Long authorId);

	@PostMapping
	ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto request);

	@PutMapping("/{authorId}")
	ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long authorId, @RequestBody AuthorDto request);

	@DeleteMapping("/{authorId}")
	ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId);
}
