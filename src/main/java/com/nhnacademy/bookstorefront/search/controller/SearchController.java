package com.nhnacademy.bookstorefront.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.search.dto.BookSearchResponse;
import com.nhnacademy.bookstorefront.search.service.impl.SearchServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/search")
public class SearchController {

	private final SearchServiceImpl searchService;

	@GetMapping
	public String search(@PageableDefault(page = 1, size = 20) Pageable pageable,
		@RequestParam String searchType,
		@RequestParam String query,
		Model model) {
		Page<BookSearchResponse> searchResult;

		switch (searchType) {
			case "books":
				searchResult = searchService.searchBooks(query, pageable);
				break;
			case "authors":
				searchResult = searchService.searchAuthors(query, pageable);
				break;
			case "publisher":
				searchResult = searchService.searchPublishers(query, pageable);
				break;
			case "tag":
				searchResult = searchService.searchBooksByTag(query, pageable);
				break;
			default:
				throw new IllegalArgumentException("Invalid search type: " + searchType);
		}

		model.addAttribute("searchResults", searchResult);
		PagingModel.pagingProcessing(pageable, model, searchResult, "/search?searchType=" + searchType + "&query=" + query, 5);

		return "book/list-book-by-category";
	}
}