package com.nhnacademy.bookstorefront.tag.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;
import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;
import com.nhnacademy.bookstorefront.tag.service.impl.TagServiceImpl;

@WebMvcTest(TagController.class)
class TagControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private TagServiceImpl tagService;

	@MockBean
	private GlobalDataControllerAdvice globalDataControllerAdvice;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new TagController(tagService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	void testCreateTagForm() throws Exception {
		mockMvc.perform(get("/api/tags/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("tag/create-tag"));
	}

	@Test
	void testUpdateTagForm() throws Exception {
		TagDto tagDto = new TagDto(1L, "TagName");
		when(tagService.getTag(1L)).thenReturn(tagDto);

		mockMvc.perform(get("/api/tags/update/1"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("tag"))
			.andExpect(model().attribute("tag", tagDto))
			.andExpect(view().name("tag/update-tag"));
	}

	@Test
	void testGetTags() throws Exception {
		TagDto tagDto = new TagDto(1L, "TagName");
		when(tagService.getTags()).thenReturn(Collections.singletonList(tagDto));

		mockMvc.perform(get("/api/tags"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("tags"))
			.andExpect(view().name("tag/list-tag"));
	}

	@Test
	void testGetTagsWithPagination() throws Exception {
		TagDto tagDto = new TagDto(1L, "TagName");
		List<TagDto> tagDtoList = List.of(tagDto);
		Page<TagDto> page = new PageImpl<>(tagDtoList, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "tagId")),
			tagDtoList.size());

		Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "tagId"));
		when(tagService.getTags(pageable)).thenReturn(page);

		mockMvc.perform(get("/api/tags/page")

				.param("page", "0")
				.param("size", "10")
				.param("sort", "tagId,asc"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("tags"))
			.andExpect(view().name("tag/list-tag"));
	}

	@Test
	void testCreateTag() throws Exception {
		TagDto request = new TagDto(null, "New Tag");

		mockMvc.perform(post("/api/tags")
				.flashAttr("tagDto", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/tags/page"));
	}

	@Test
	void testUpdateTag() throws Exception {
		TagDto request = new TagDto(null, "Updated Tag");

		mockMvc.perform(put("/api/tags/1")
				.flashAttr("tagDto", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/tags/page"));
	}

	@Test
	void testDeleteTag() throws Exception {
		mockMvc.perform(delete("/api/tags/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/tags/page"));
	}
}
