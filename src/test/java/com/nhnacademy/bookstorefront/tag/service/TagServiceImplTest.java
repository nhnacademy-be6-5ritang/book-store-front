package com.nhnacademy.bookstorefront.tag.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;
import com.nhnacademy.bookstorefront.tag.feignclient.TagServiceClient;
import com.nhnacademy.bookstorefront.tag.service.impl.TagServiceImpl;

class TagServiceImplTest {

	@Mock
	private TagServiceClient tagServiceClient;

	@InjectMocks
	private TagServiceImpl tagServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetTags() {
		TagDto tagDto = new TagDto(1L, "TagName");
		when(tagServiceClient.getTags()).thenReturn(ResponseEntity.ok(Collections.singletonList(tagDto)));

		List<TagDto> tags = tagServiceImpl.getTags();

		assertThat(tags).isNotNull();
		assertThat(tags).hasSize(1);
		assertThat(tags.get(0).tagId()).isEqualTo(1L);
		assertThat(tags.get(0).tagName()).isEqualTo("TagName");
	}

	@Test
	void testGetTagsWithPagination() {
		TagDto tagDto = new TagDto(1L, "TagName");
		Pageable pageable = PageRequest.of(0, 10);
		Page<TagDto> page = new PageImpl<>(Collections.singletonList(tagDto), pageable, 1);
		when(tagServiceClient.getTags(any(Pageable.class))).thenReturn(ResponseEntity.ok(page));

		Page<TagDto> tags = tagServiceImpl.getTags(pageable);

		assertThat(tags).isNotNull();
		assertThat(tags.getContent()).hasSize(1);
		assertThat(tags.getContent().get(0).tagId()).isEqualTo(1L);
		assertThat(tags.getContent().get(0).tagName()).isEqualTo("TagName");
	}

	@Test
	void testGetTagsByBookId() {
		TagDto tagDto = new TagDto(1L, "TagName");
		when(tagServiceClient.getTagsByBookId(anyLong())).thenReturn(
			ResponseEntity.ok(Collections.singletonList(tagDto)));

		List<TagDto> tags = tagServiceImpl.getTagsByBookId(1L);

		assertThat(tags).isNotNull();
		assertThat(tags).hasSize(1);
		assertThat(tags.get(0).tagId()).isEqualTo(1L);
		assertThat(tags.get(0).tagName()).isEqualTo("TagName");
	}

	@Test
	void testGetTag() {
		TagDto tagDto = new TagDto(1L, "TagName");
		when(tagServiceClient.getTag(anyLong())).thenReturn(ResponseEntity.ok(tagDto));

		TagDto tag = tagServiceImpl.getTag(1L);

		assertThat(tag).isNotNull();
		assertThat(tag.tagId()).isEqualTo(1L);
		assertThat(tag.tagName()).isEqualTo("TagName");
	}

	@Test
	void testCreateTag() {
		TagDto tagDto = new TagDto(1L, "TagName");
		when(tagServiceClient.createTag(any(TagDto.class))).thenReturn(ResponseEntity.ok(tagDto));

		TagDto createdTag = tagServiceImpl.createTag(tagDto);

		assertThat(createdTag).isNotNull();
		assertThat(createdTag.tagId()).isEqualTo(1L);
		assertThat(createdTag.tagName()).isEqualTo("TagName");
	}

	@Test
	void testUpdateTag() {
		TagDto tagDto = new TagDto(1L, "TagName");
		when(tagServiceClient.updateTag(anyLong(), any(TagDto.class))).thenReturn(ResponseEntity.ok(tagDto));

		TagDto updatedTag = tagServiceImpl.updateTag(1L, tagDto);

		assertThat(updatedTag).isNotNull();
		assertThat(updatedTag.tagId()).isEqualTo(1L);
		assertThat(updatedTag.tagName()).isEqualTo("TagName");
	}

	@Test
	void testDeleteTag() {
		// No return value to verify, just ensure no exception is thrown
		tagServiceImpl.deleteTag(1L);
	}
}
