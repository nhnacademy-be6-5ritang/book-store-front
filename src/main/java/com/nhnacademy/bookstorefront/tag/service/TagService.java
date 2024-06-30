package com.nhnacademy.bookstorefront.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;
import com.nhnacademy.bookstorefront.tag.feignclient.TagServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
	private final TagServiceClient tagServiceClient;

	public List<TagDto> getTags() {
		return tagServiceClient.getTags().getBody();
	}

	public TagDto getTag(Long tagId) {
		return tagServiceClient.getTag(tagId).getBody();
	}

	public TagDto createTag(TagDto tagDto) {
		return tagServiceClient.createTag(tagDto).getBody();
	}

	public TagDto updateTag(Long tagId, TagDto tagDto) {
		return tagServiceClient.updateTag(tagId, tagDto).getBody();
	}

	public void deleteTag(Long tagId) {
		tagServiceClient.deleteTag(tagId);
	}
}
