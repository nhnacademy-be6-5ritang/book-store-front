package com.nhnacademy.bookstorefront.tag.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;
import com.nhnacademy.bookstorefront.tag.feignclient.TagServiceClient;
import com.nhnacademy.bookstorefront.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
	private final TagServiceClient tagServiceClient;

	@Override
	public List<TagDto> getTags() {
		return tagServiceClient.getTags().getBody();
	}

	@Override
	public Page<TagDto> getTags(Pageable pageable) {
		return tagServiceClient.getTags(pageable).getBody();
	}

	@Override
	public List<TagDto> getTagsByBookId(Long bookId) {
		return tagServiceClient.getTagsByBookId(bookId).getBody();
	}

	@Override
	public TagDto getTag(Long tagId) {
		return tagServiceClient.getTag(tagId).getBody();
	}

	@Override
	public TagDto createTag(TagDto tagDto) {
		return tagServiceClient.createTag(tagDto).getBody();
	}

	@Override
	public TagDto updateTag(Long tagId, TagDto tagDto) {
		return tagServiceClient.updateTag(tagId, tagDto).getBody();
	}

	@Override
	public void deleteTag(Long tagId) {
		tagServiceClient.deleteTag(tagId);
	}
}
