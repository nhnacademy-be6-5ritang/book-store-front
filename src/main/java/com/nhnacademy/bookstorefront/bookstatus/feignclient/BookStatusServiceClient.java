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

@FeignClient(name = "bookStatus-feign-client", url = "http://localhost:8083")
public interface BookStatusServiceClient {

	@GetMapping("/bookStatuses")
	ResponseEntity<List<BookStatusDto>> getBookStatuses();

	@GetMapping("/bookStatuses/{bookStatusId}")
	ResponseEntity<BookStatusDto> getBookStatus(@PathVariable Long bookStatusId);

	@PostMapping("/bookStatuses")
	ResponseEntity<BookStatusDto> createBookStatus(@RequestBody BookStatusDto request);

	@PutMapping("/bookStatuses/{bookStatusId}")
	ResponseEntity<BookStatusDto> updateBookStatus(@PathVariable Long bookStatusId,
		@RequestBody BookStatusDto request);

	@DeleteMapping("/bookStatuses/{bookStatusId}")
	ResponseEntity<Void> deleteBookStatus(@PathVariable Long bookStatusId);
}
