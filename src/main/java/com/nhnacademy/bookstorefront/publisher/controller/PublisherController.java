package com.nhnacademy.bookstorefront.publisher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;
import com.nhnacademy.bookstorefront.publisher.service.PublisherService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/publishers")
public class PublisherController {
	private final PublisherService publisherService;

	@GetMapping("/create")
	public String createPublisherForm() {
		return "publisher/create-publisher";
	}

	@GetMapping("/update/{publisherId}")
	public String updatePublisherForm(@PathVariable Long publisherId, Model model) {
		model.addAttribute("publisher", publisherService.getPublisher(publisherId));
		return "publisher/update-publisher";
	}

	@GetMapping
	public String getPublishers(Model model) {
		model.addAttribute("publishers", publisherService.getPublishers());
		return "publisher/list-publisher";
	}

	@PostMapping
	public String createPublisher(@ModelAttribute PublisherDto request) {
		publisherService.createPublisher(request);
		return "redirect:/publishers";
	}

	@PutMapping("/{publisherId}")
	public String updatePublisher(@PathVariable Long publisherId, @ModelAttribute PublisherDto request) {
		publisherService.updatePublisher(publisherId, request);
		return "redirect:/publishers";
	}

	@DeleteMapping("/{publisherId}")
	public String deletePublisher(@PathVariable Long publisherId) {
		publisherService.deletePublisher(publisherId);
		return "redirect:/publishers";
	}
}
