package com.nhnacademy.bookstorefront.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.book.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
	private final BookService bookService;

	@GetMapping
	public String index(Model model) {
		model.addAttribute("books", bookService.findAllBooks());
		return "index";
	}

	@GetMapping("/{bookId}")
	public String getBook(@PathVariable Long bookId, Model model) {
		model.addAttribute("book", bookService.findBookById(bookId));
		return "book/get-book";
	}
}
