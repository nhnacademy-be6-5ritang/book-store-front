package com.nhnacademy.bookstorefront.publisher.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
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
	ResponseEntity<List<PublisherDto>> getPublishers();

	@GetMapping("/{publisherId}")
	ResponseEntity<PublisherDto> getPublisher(@PathVariable Long publisherId);

	@PostMapping
	ResponseEntity<PublisherDto> createPublisher(
		@RequestBody PublisherDto request);

	@PutMapping("/{publisherId}")
	ResponseEntity<PublisherDto> updatePublisher(@PathVariable Long publisherId,
		@RequestBody PublisherDto request);

	@DeleteMapping("/{publisherId}")
	ResponseEntity<Void> deletePublisher(@PathVariable Long publisherId);
}
