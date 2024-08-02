package com.nhnacademy.bookstorefront.search.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.search.dto.BookSearchResponse;
import com.nhnacademy.bookstorefront.search.service.impl.SearchServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * @author 김기욱
 * 검색 관련 HTTP 요청을 처리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("api/search")
public class SearchController {
	private final SearchServiceImpl searchService;

	/**
	 * 검색 요청을 처리합니다.
	 * 사용자가 입력한 검색어와 검색 유형을 기반으로 검색을 수행하고,
	 * 결과를 페이지네이션하여 뷰에 전달합니다.
	 *
	 * @param pageable 페이지네이션 정보 (기본값: 페이지 1, 크기 12, 책 출판일 기준 내림차순 정렬)
	 * @param sortBy 정렬 기준 필드 (기본값: bookPublishDate)
	 * @param direction 정렬 방향 (기본값: DESC)
	 * @param searchType 검색 유형 (books, authors, publisher, tag 중 하나)
	 * @param query 검색어
	 * @param model 뷰에 데이터를 전달하기 위한 모델 객체
	 * @return 검색 결과 페이지의 뷰 이름
	 * @throws IllegalArgumentException 유효하지 않은 검색 유형이 전달된 경우
	 */
	@GetMapping
	public String search(
		@PageableDefault(page = 1, size = 12, sort = {
			"bookPublishDate"}, direction = Sort.Direction.DESC) Pageable pageable,
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
		PagingModel.pagingProcessing(sortedPageable, model, searchResult,
			"/api/search?searchType=" + searchType + "&query=" + query + "&sortBy=" + sortBy + "&direction="
				+ direction, 5);

		return "search/search-result";
	}
}
