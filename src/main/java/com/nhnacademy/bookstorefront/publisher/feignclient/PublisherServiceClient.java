package com.nhnacademy.bookstorefront.publisher.feignclient;

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

import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;

@FeignClient(name = "publisher-feign-client", url = "http://localhost:8090/api/publishers")
public interface PublisherServiceClient {

	@GetMapping
	ResponseEntity<Page<PublisherDto>> getPublishers(Pageable pageable);

	@GetMapping("/{publisherId}")
	ResponseEntity<PublisherDto> getPublisher(@PathVariable Long publisherId);

	@PostMapping
	ResponseEntity<Void> createPublisher(
		@RequestBody PublisherDto request);

	@PutMapping("/{publisherId}")
	ResponseEntity<Void> updatePublisher(@PathVariable Long publisherId,
		@RequestBody PublisherDto request);

	@DeleteMapping("/{publisherId}")
	ResponseEntity<Void> deletePublisher(@PathVariable Long publisherId);
}
