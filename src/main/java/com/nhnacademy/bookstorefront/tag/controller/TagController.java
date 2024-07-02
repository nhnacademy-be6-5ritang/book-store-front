package com.nhnacademy.bookstorefront.tag.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;
import com.nhnacademy.bookstorefront.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
	private final TagService tagService;

	@GetMapping("/create")
	public String createTagForm() {
		return "tag/create-tag";
	}

	@GetMapping("/update/{tagId}")
	public String updateTagForm(@PathVariable Long tagId, Model model) {
		model.addAttribute("tag", tagService.getTag(tagId));
		return "tag/update-tag";
	}

	@GetMapping
	public String getTags(Model model) {
		model.addAttribute("tags", tagService.getTags());
		return "tag/list-tag";
	}

	@GetMapping("/page")
	public String getTags(@PageableDefault(page = 1) Pageable pageable, Model model) {
		Page<TagDto> tags = tagService.getTags(pageable);
		model.addAttribute("tags", tags);
		model.addAttribute("objects", tags); // 공통 객체 이름
		model.addAttribute("baseUrl", "/tags/page"); // 페이징 URL

		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), tags.getTotalPages());

		model.addAttribute("pageable", pageable);
		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "tag/list-tag";
	}

	@PostMapping
	public String createTag(@ModelAttribute TagDto request) {
		tagService.createTag(request);
		return "redirect:/tags/page";
	}

	@PutMapping("/{tagId}")
	public String updateTag(@PathVariable Long tagId, @ModelAttribute TagDto request) {
		tagService.updateTag(tagId, request);
		return "redirect:/tags/page";
	}

	@DeleteMapping("/{tagId}")
	public String deleteTag(@PathVariable Long tagId) {
		tagService.deleteTag(tagId);
		return "redirect:/tags/page";
	}
}
