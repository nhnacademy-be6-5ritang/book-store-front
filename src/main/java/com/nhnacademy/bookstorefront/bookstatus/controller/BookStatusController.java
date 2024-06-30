package com.nhnacademy.bookstorefront.bookstatus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.bookstatus.dto.response.BookStatusDto;
import com.nhnacademy.bookstorefront.bookstatus.service.BookStatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookStatuses")
public class BookStatusController {
	private final BookStatusService bookStatusService;

	@GetMapping("/create")
	public String createBookStatusForm() {
		return "bookStatus/create-bookStatus";
	}

	@GetMapping("/update/{bookStatusId}")
	public String updateBookStatusForm(@PathVariable Long bookStatusId, Model model) {
		model.addAttribute("bookStatus", bookStatusService.getBookStatus(bookStatusId));
		return "bookStatus/update-bookStatus";
	}

	@GetMapping
	public String getBookStatuses(Model model) {
		model.addAttribute("bookStatuses", bookStatusService.getBookStatuses());
		return "bookStatus/list-bookStatus";
	}

	@PostMapping
	public String createBookStatus(@ModelAttribute BookStatusDto request) {
		bookStatusService.createBookStatus(request);
		return "redirect:/bookStatuses";
	}

	@PutMapping("/{bookStatusId}")
	public String updateBookStatus(@PathVariable Long bookStatusId, @ModelAttribute BookStatusDto request) {
		bookStatusService.updateBookStatus(bookStatusId, request);
		return "redirect:/bookStatuses";
	}

	@DeleteMapping("/{bookStatusId}")
	public String deleteBookStatus(@PathVariable Long bookStatusId) {
		bookStatusService.deleteBookStatus(bookStatusId);
		return "redirect:/bookStatuses";
	}
}
