package com.nhnacademy.bookstorefront.global.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

/**
 *  * @author 이경헌
 * 페이지네이션 처리를 수행하고 필요한 속성을 모델에 추가하는 유틸리티 클래스입니다.
 */
public class PagingModel {
	// 인스턴스화를 방지하기 위한 private 생성자
	private PagingModel() {
	}

	/**
	 * 페이지네이션을 처리하고 필요한 속성을 모델에 추가합니다.
	 *
	 * @param pageable 페이지네이션 정보를 담고 있는 Pageable 객체
	 * @param model 속성을 추가할 Model 객체
	 * @param objects 페이지네이션된 항목들을 담고 있는 Page 객체
	 * @param baseUrl 페이지네이션 링크의 기본 URL
	 * @param inputBlockLimit 표시할 페이지네이션 블록의 제한
	 * @param <T> Page 객체에 있는 항목들의 타입
	 */
	public static <T> void pagingProcessing(Pageable pageable, Model model, Page<T> objects, String baseUrl,
		int inputBlockLimit) {
		model.addAttribute("objects", objects); // 공통 객체 이름
		model.addAttribute("baseUrl", baseUrl); // 페이징 URL

		int blockLimit = 3;
		int startPage = 1; // 1 4 7 10 ~~
		int endPage = 1;

		if (!objects.isEmpty()) {
			blockLimit = inputBlockLimit;
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
