package com.nhnacademy.bookstorefront.bookstatus.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.bookstatus.dto.response.BookStatusDto;

@FeignClient(name = "bookStatus-feign-client", url = "http://localhost:8090/api/bookStatuses")
public interface BookStatusServiceClient {

	@GetMapping
	ResponseEntity<List<BookStatusDto>> getBookStatuses();

	@GetMapping("/{bookStatusId}")
	ResponseEntity<BookStatusDto> getBookStatus(@PathVariable Long bookStatusId);

	@PostMapping
	ResponseEntity<Void> createBookStatus(@RequestBody BookStatusDto request);

	@PutMapping("/{bookStatusId}")
	ResponseEntity<Void> updateBookStatus(@PathVariable Long bookStatusId,
		@RequestBody BookStatusDto request);

	@DeleteMapping("/{bookStatusId}")
	ResponseEntity<Void> deleteBookStatus(@PathVariable Long bookStatusId);
}
