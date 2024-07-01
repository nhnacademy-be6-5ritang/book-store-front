package com.nhnacademy.bookstorefront.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.bookstatus.service.BookStatusService;
import com.nhnacademy.bookstorefront.category.service.CategoryService;
import com.nhnacademy.bookstorefront.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
	private final BookService bookService;
	private final CategoryService categoryService;
	private final BookStatusService bookStatusService;
	private final TagService tagService;

	@GetMapping("/create")
	public String createBookForm(Model model) {
		model.addAttribute("bookStatuses", bookStatusService.getBookStatuses());
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("tags", tagService.getTags());
		return "book/create-book";
	}

	@GetMapping("/update/{bookId}")
	public String updateBookForm(@PathVariable Long bookId, Model model) {
		model.addAttribute("book", bookService.getBook(bookId));
		return "book/update-book";
	}

	@GetMapping("/main")
	public String main(Model model) {
		model.addAttribute("books", bookService.findAllBooks());
		return "index";
	}

	@GetMapping
	public String getBooks(Model model) {
		model.addAttribute("books", bookService.findAllBooks());
		return "book/list-book";
	}

	@GetMapping("/{bookId}")
	public String getBook(@PathVariable Long bookId, Model model) {
		model.addAttribute("tags", tagService.getTagsByBookId(bookId));
		model.addAttribute("book", bookService.getBook(bookId));
		return "book/get-book";
	}

	@GetMapping("/detail/{bookId}")
	public String getBookDetail(@PathVariable Long bookId, Model model) {
		model.addAttribute("tags", tagService.getTagsByBookId(bookId));
		model.addAttribute("book", bookService.getBook(bookId));
		return "book/get-book-detail";
	}

	@PostMapping
	public String createBook(@ModelAttribute CreateBookRequest request) {
		bookService.createBook(request);
		return "redirect:/books";
	}

	// @PutMapping("/{bookId}")
	// public String updateBook(@PathVariable Long bookId, @ModelAttribute BookUpdateRequest request) {
	// 	bookService.updateBook(bookId, request);
	// 	return "redirect:/books";
	// }

	@DeleteMapping("/{bookId}")
	public String deleteBook(@PathVariable Long bookId) {
		bookService.deleteBook(bookId);
		return "redirect:/books";
	}
}
