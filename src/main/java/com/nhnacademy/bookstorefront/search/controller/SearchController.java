package com.nhnacademy.bookstorefront.search.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	public String search(@PageableDefault(page = 1, size = 20, sort = {"bookPublishDate"}, direction = Sort.Direction.DESC) Pageable pageable,
		@RequestParam(defaultValue = "bookPublishDate") String sortBy,
		@RequestParam(defaultValue = "DESC") String direction,
		@RequestParam String searchType,
		@RequestParam String query,
		Model model) {

		Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
		Sort sort = Sort.by(sortDirection, sortBy);
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
		Page<BookSearchResponse> searchResult;

		switch (searchType) {
			case "books":
				searchResult = searchService.searchBooks(query, sortedPageable);
				break;
			case "authors":
				searchResult = searchService.searchAuthors(query, sortedPageable);
				break;
			case "publisher":
				searchResult = searchService.searchPublishers(query, sortedPageable);
				break;
			case "tag":
				searchResult = searchService.searchBooksByTag(query, sortedPageable);
				break;
			default:
				throw new IllegalArgumentException("Invalid search type: " + searchType);
		}

		model.addAttribute("searchResults", searchResult);
		model.addAttribute("searchType", searchType);
		model.addAttribute("query", query);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		PagingModel.pagingProcessing(sortedPageable, model, searchResult, "/api/search?searchType=" + searchType + "&query=" + query, 5);

		return "search/search-result";
	}
}
