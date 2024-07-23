package com.nhnacademy.bookstorefront.book.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstorefront.bookstatus.service.impl.BookStatusServiceImpl;
import com.nhnacademy.bookstorefront.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.bookstorefront.global.config.CacheConfig;
import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.review.dto.response.GetReviewResponse;
import com.nhnacademy.bookstorefront.review.service.ReviewService;
import com.nhnacademy.bookstorefront.tag.service.impl.TagServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 책 관리 웹 페이지 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
	private final BookServiceImpl bookService;
	private final CategoryServiceImpl categoryService;
	private final BookStatusServiceImpl bookStatusService;
	private final TagServiceImpl tagService;
	private final ReviewService reviewService;
	private final CacheConfig cacheConfig;
	private static final String REDIRECT_URL = "redirect:/api/books/page";

	/**
	 * 책 생성 폼을 반환합니다.
	 *
	 * @param model 모델 객체
	 * @return 책 생성 폼 뷰 이름
	 */
	@GetMapping("/create")
	public String createBookForm(Model model) {
		model.addAttribute("bookStatuses", bookStatusService.getBookStatuses());
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("tags", tagService.getTags());
		return "book/create-book";
	}

	/**
	 * 주어진 책 ID에 해당하는 책 정보를 수정하는 폼을 반환합니다.
	 *
	 * @param bookId 책 ID
	 * @param model  모델 객체
	 * @return 책 수정 폼 뷰 이름
	 */
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

	/**
	 * 메인 페이지에서 모든 책을 조회합니다.
	 *
	 * @param model 모델 객체
	 * @return 메인 페이지 뷰 이름
	 */
	@GetMapping("/main")
	public String mainPage(Model model) {
		model.addAttribute("orderedBooksCache", cacheConfig.getOrderedBooks());
		model.addAttribute("likesBooksCache", cacheConfig.getLikesBooks());
		model.addAttribute("newestBooksCache", cacheConfig.getNewestBooks());
		return "index";
	}

	/**
	 * 페이지네이션을 적용하여 모든 책을 조회합니다.
	 *
	 * @param pageable 페이지 정보
	 * @param model    모델 객체
	 * @return 책 리스트 뷰 이름
	 */
	@GetMapping("/page")
	public String findAllBooks(@PageableDefault(page = 1) Pageable pageable, Model model) {
		Page<GetBookDetailResponse> books = bookService.getNewestBooks(pageable);
		model.addAttribute("books", books);
		PagingModel.pagingProcessing(pageable, model, books, "/api/books/page", 5);

		return "book/list-book";
	}

	@GetMapping("/page/category")
	public String findAllBooksByCategoryName(@PageableDefault(page = 1, size = 10) Pageable pageable,
		@RequestParam String categoryName, Model model) {
		Page<GetBookDetailResponse> books = bookService.findAllBooksByCategory(pageable, categoryName);
		model.addAttribute("books", books);
		model.addAttribute("categoryName", categoryName);
		PagingModel.pagingProcessing(pageable, model, books, "/api/books/page/category?categoryName=" + categoryName,
			5);

		return "book/list-book-by-category";
	}

	/**
	 * 주어진 책 ID에 해당하는 책 정보를 조회합니다.
	 *
	 * @param bookId 책 ID
	 * @param model  모델 객체
	 * @return 책 정보 뷰 이름
	 */
	@GetMapping("/{bookId}")
	public String getBook(@PathVariable Long bookId, Model model,
		@PageableDefault(page = 1, size = 5) Pageable pageable, @RequestParam(required = false) String reviewType) {
		model.addAttribute("bookCategories", categoryService.getCategoriesByBookId(bookId));
		model.addAttribute("bookTags", tagService.getTagsByBookId(bookId));
		model.addAttribute("book", bookService.getBook(bookId));

		model.addAttribute("reviewsAverageScore", reviewService.getReviewsAverageScoreByBookId(bookId));

		Page<GetReviewResponse> reviews = null;
		if (reviewType != null && reviewType.equals("일반")) {
			reviews = reviewService.getGeneralReviewsByBookId(pageable, bookId);
		} else if (reviewType != null && reviewType.equals("사진")) {
			reviews = reviewService.getPhotoReviewsByBookId(pageable, bookId);
		} else {
			reviews = reviewService.getReviewsByBookId(pageable, bookId);
		}

		model.addAttribute("reviewType", reviewType);
		model.addAttribute("reviews", reviews);
		int blockLimit = 5;
		int startPage = 1; // 1 4 7 10 ~~
		int endPage = 1;

		if (!reviews.isEmpty()) {
			// 검색 결과가 있는 경우에만 페이지 번호 계산
			int adjustedPage = Math.max(pageable.getPageNumber(), 1);
			startPage = (((int)(Math.ceil((double)adjustedPage / blockLimit))) - 1) * blockLimit + 1;
			endPage = Math.min((startPage + blockLimit - 1), reviews.getTotalPages());
		}

		model.addAttribute("pageable", pageable);
		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "book/get-book";
	}

	/**
	 * 주어진 책 ID에 해당하는 책의 상세 정보를 조회합니다.
	 *
	 * @param bookId 책 ID
	 * @param model  모델 객체
	 * @return 책 상세 정보 뷰 이름
	 */
	@GetMapping("/detail/{bookId}")
	public String getBookDetail(@PathVariable Long bookId, Model model) {
		model.addAttribute("bookCategories", categoryService.getCategoriesByBookId(bookId));
		model.addAttribute("bookTags", tagService.getTagsByBookId(bookId));
		model.addAttribute("book", bookService.getBook(bookId));
		return "book/get-book-detail";
	}

	/**
	 * ISBN 을 통한 도서정보 조회 및 저장
	 *
	 * @param isbn 도서 ISBN
	 * @return 도서저장결과
	 */
	@PostMapping("/fetch")
	String fetchAndSaveBook(@RequestParam String isbn) {
		bookService.saveBookByIsbn(isbn);
		return REDIRECT_URL;
	}

	/**
	 * 새로운 책을 생성합니다.
	 *
	 * @param request 생성할 책 정보 DTO
	 * @return 책 리스트 페이지로 리다이렉트
	 */
	@PostMapping
	public String createBook(@Valid @ModelAttribute CreateBookRequest request) {
		bookService.createBook(request);
		return REDIRECT_URL;
	}

	/**
	 * 주어진 책 ID에 해당하는 책 정보를 수정합니다.
	 *
	 * @param bookId  수정할 책 ID
	 * @param request 수정할 책 정보 DTO
	 * @return 수정된 책 정보 페이지로 리다이렉트
	 */
	@PutMapping("/{bookId}")
	public String updateBookById(@PathVariable Long bookId, @Valid @ModelAttribute UpdateBookRequest request) {
		bookService.updateBookById(bookId, request);
		return "redirect:/api/books/detail/" + bookId;
	}

	/**
	 * 주어진 책 ID에 해당하는 책 정보를 삭제합니다.
	 *
	 * @param bookId 삭제할 책 ID
	 * @return 책 리스트 페이지로 리다이렉트
	 */
	@DeleteMapping("/{bookId}")
	public String deleteBook(@PathVariable Long bookId) {
		bookService.deleteBook(bookId);
		return REDIRECT_URL;
	}

	@GetMapping("/search/test")
	public ResponseEntity<List<BookSearchResult>> searchBooks(@RequestParam("key") String search) {
		List<BookSearchResult> results = bookService.searchBooks(search);
		return ResponseEntity.ok(results);
	}

}
