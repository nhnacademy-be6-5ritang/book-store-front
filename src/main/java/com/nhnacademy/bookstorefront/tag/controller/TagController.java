package com.nhnacademy.bookstorefront.tag.controller;

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

	@PostMapping
	public String createTag(@ModelAttribute TagDto request) {
		tagService.createTag(request);
		return "redirect:/tags";
	}

	@PutMapping("/{tagId}")
	public String updateTag(@PathVariable Long tagId, @ModelAttribute TagDto request) {
		tagService.updateTag(tagId, request);
		return "redirect:/tags";
	}

	@DeleteMapping("/{tagId}")
	public String deleteTag(@PathVariable Long tagId) {
		tagService.deleteTag(tagId);
		return "redirect:/tags";
	}
}
