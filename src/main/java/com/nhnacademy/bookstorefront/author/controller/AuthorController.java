package com.nhnacademy.bookstorefront.author.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;
import com.nhnacademy.bookstorefront.author.service.AuthorService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
	private final AuthorService authorService;

	@GetMapping("/create")
	public String createAuthorForm() {
		return "author/create-author";
	}

	@GetMapping("/update/{authorId}")
	public String updateAuthorForm(@PathVariable Long authorId, Model model) {
		model.addAttribute("author", authorService.getAuthor(authorId));
		return "author/update-author";
	}

	@GetMapping
	public String getAuthors(Model model) {
		model.addAttribute("authors", authorService.getAuthors());
		return "author/list-author";
	}

	@PostMapping
	public String createAuthor(@ModelAttribute AuthorDto request) {
		authorService.createAuthor(request);
		return "redirect:/authors";
	}

	@PutMapping("/{authorId}")
	public String updateAuthor(@PathVariable Long authorId, @ModelAttribute AuthorDto request) {
		authorService.updateAuthor(authorId, request);
		return "redirect:/authors";
	}

	@DeleteMapping("/{authorId}")
	public String deleteAuthor(@PathVariable Long authorId) {
		authorService.deleteAuthor(authorId);
		return "redirect:/authors";
	}
}
