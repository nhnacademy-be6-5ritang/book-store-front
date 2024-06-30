package com.nhnacademy.bookstorefront.tag.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;

@FeignClient(name = "tag-feign-client", url = "http://localhost:8083")
public interface TagServiceClient {

	@GetMapping("/tags")
	ResponseEntity<List<TagDto>> getTags();

	@GetMapping("/tags/{tagId}")
	ResponseEntity<TagDto> getTag(@PathVariable Long tagId);

	@PostMapping("/tags")
	ResponseEntity<TagDto> createTag(
		@RequestBody TagDto request);

	@PutMapping("/tags/{tagId}")
	ResponseEntity<TagDto> updateTag(@PathVariable Long tagId,
		@RequestBody TagDto request);

	@DeleteMapping("/tags/{tagId}")
	ResponseEntity<Void> deleteTag(@PathVariable Long tagId);
}
