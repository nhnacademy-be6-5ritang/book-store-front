package com.nhnacademy.bookstorefront.book.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.book.dto.request.BookUpdateRequest;
import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.bookstatus.service.BookStatusService;
import com.nhnacademy.bookstorefront.category.service.CategoryService;
import com.nhnacademy.bookstorefront.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books")
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
		model.addAttribute("bookStatuses", bookStatusService.getBookStatuses());
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("tags", tagService.getTags());
		model.addAttribute("bookCategories", categoryService.getCategoriesByBookId(bookId));
		model.addAttribute("bookTags", tagService.getTagsByBookId(bookId));
		model.addAttribute("book", bookService.getBook(bookId));
		return "book/update-book";
	}

	@GetMapping("/main")
	public String mainPage(Model model) {
		model.addAttribute("books", bookService.findAllBooks());
		return "index";
	}

	@GetMapping("/page")
	public String findAllBooks(@PageableDefault(page = 1) Pageable pageable, Model model) {
		Page<GetBookDetailResponse> books = bookService.findAllBooks(pageable);
		model.addAttribute("books", books);
		model.addAttribute("objects", books); // 공통 객체 이름
		model.addAttribute("baseUrl", "/api/books/page"); // 페이징 URL

		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), books.getTotalPages());

		model.addAttribute("pageable", pageable);
		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "book/list-book";
	}

	@GetMapping("/{bookId}")
	public String findAllBooks(@PathVariable Long bookId, Model model) {
		model.addAttribute("bookCategories", categoryService.getCategoriesByBookId(bookId));
		model.addAttribute("bookTags", tagService.getTagsByBookId(bookId));
		model.addAttribute("book", bookService.getBook(bookId));
		return "book/get-book";
	}

	@GetMapping("/detail/{bookId}")
	public String getBookDetail(@PathVariable Long bookId, Model model) {
		model.addAttribute("bookCategories", categoryService.getCategoriesByBookId(bookId));
		model.addAttribute("bookTags", tagService.getTagsByBookId(bookId));
		model.addAttribute("book", bookService.getBook(bookId));
		return "book/get-book-detail";
	}

	@PostMapping
	public String createBook(@ModelAttribute CreateBookRequest request) {
		bookService.createBook(request);
		return "redirect:/api/books";
	}

	@PutMapping("/{bookId}")
	public String updateBookById(@PathVariable Long bookId, @ModelAttribute UpdateBookRequest request) {
		bookService.updateBookById(bookId, request);
		return "redirect:/api/books/detail/" + bookId;
	}

	@PatchMapping("/{isbn}")
	public GetBookDetailResponse updateBookByIsbn(@PathVariable String isbn, @ModelAttribute BookUpdateRequest request) {
		return bookService.updateBookByIsbn(isbn, request);
	}

	@DeleteMapping("/{bookId}")
	public String deleteBook(@PathVariable Long bookId) {
		bookService.deleteBook(bookId);
		return "redirect:/api/books";
	}
}
