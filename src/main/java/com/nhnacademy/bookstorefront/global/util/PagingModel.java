package com.nhnacademy.bookstorefront.global.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public class PagingModel {
	private PagingModel() {
	}

	public static <T> void pagingProcessing(Pageable pageable, Model model, Page<T> objects, String baseUrl,
		int blockLimit) {
		model.addAttribute("objects", objects); // 공통 객체 이름
		model.addAttribute("baseUrl", baseUrl); // 페이징 URL

		int baseBlockLimit = 3;
		int startPage = 1; // 1 4 7 10 ~~
		int endPage = 1;

		if (!objects.isEmpty()) {
			// 검색 결과가 있는 경우에만 페이지 번호 계산
			int adjustedPage = Math.max(pageable.getPageNumber(), 1);
			startPage = (((int)(Math.ceil((double)adjustedPage / blockLimit))) - 1) * blockLimit + 1;
			endPage = Math.min((startPage + blockLimit - 1), objects.getTotalPages());
		}

		model.addAttribute("pageable", pageable);
		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
	}
}
