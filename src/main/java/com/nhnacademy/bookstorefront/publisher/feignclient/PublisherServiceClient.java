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

@FeignClient(name = "publisher-feign-client", url = "http://localhost:8083")
public interface PublisherServiceClient {

	@GetMapping("/publishers")
	ResponseEntity<List<PublisherDto>> getPublishers();

	@GetMapping("/publishers/{publisherId}")
	ResponseEntity<PublisherDto> getPublisher(@PathVariable Long publisherId);

	@PostMapping("/publishers")
	ResponseEntity<PublisherDto> createPublisher(
		@RequestBody PublisherDto request);

	@PutMapping("/publishers/{publisherId}")
	ResponseEntity<PublisherDto> updatePublisher(@PathVariable Long publisherId,
		@RequestBody PublisherDto request);

	@DeleteMapping("/publishers/{publisherId}")
	ResponseEntity<Void> deletePublisher(@PathVariable Long publisherId);
}
