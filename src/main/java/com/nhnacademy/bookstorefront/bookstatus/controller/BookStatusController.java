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

/**
 * 책 상태 관리 웹 페이지 컨트롤러입니다.
 * 이 컨트롤러는 책 상태 정보를 생성, 조회, 수정, 삭제하는 기능을 제공합니다.
 * 또한 웹 페이지에서 사용할 뷰를 반환합니다.
 *
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/bookStatuses")
public class BookStatusController {
	private final BookStatusService bookStatusService;

	/**
	 * 책 상태 생성 폼을 반환합니다.
	 *
	 * @return 책 상태 생성 폼 뷰 이름
	 */
	@GetMapping("/create")
	public String createBookStatusForm() {
		return "bookStatus/create-bookStatus";
	}

	/**
	 * 주어진 책 상태 ID에 해당하는 책 상태 정보를 수정하는 폼을 반환합니다.
	 *
	 * @param bookStatusId 책 상태 ID
	 * @param model        모델 객체
	 * @return 책 상태 수정 폼 뷰 이름
	 */
	@GetMapping("/update/{bookStatusId}")
	public String updateBookStatusForm(@PathVariable Long bookStatusId, Model model) {
		model.addAttribute("bookStatus", bookStatusService.getBookStatus(bookStatusId));
		return "bookStatus/update-bookStatus";
	}

	/**
	 * 모든 책 상태 정보를 조회합니다.
	 *
	 * @param model 모델 객체
	 * @return 책 상태 리스트 뷰 이름
	 */
	@GetMapping
	public String getBookStatuses(Model model) {
		model.addAttribute("bookStatuses", bookStatusService.getBookStatuses());
		return "bookStatus/list-bookStatus";
	}

	/**
	 * 새로운 책 상태 정보를 생성합니다.
	 *
	 * @param request 생성할 책 상태 정보 DTO
	 * @return 책 상태 리스트 페이지로 리다이렉트
	 */
	@PostMapping
	public String createBookStatus(@ModelAttribute BookStatusDto request) {
		bookStatusService.createBookStatus(request);
		return "redirect:/api/bookStatuses";
	}

	/**
	 * 주어진 책 상태 ID에 해당하는 책 상태 정보를 수정합니다.
	 *
	 * @param bookStatusId 수정할 책 상태 ID
	 * @param request      수정할 책 상태 정보 DTO
	 * @return 책 상태 리스트 페이지로 리다이렉트
	 */
	@PutMapping("/{bookStatusId}")
	public String updateBookStatus(@PathVariable Long bookStatusId, @ModelAttribute BookStatusDto request) {
		bookStatusService.updateBookStatus(bookStatusId, request);
		return "redirect:/api/bookStatuses";
	}

	/**
	 * 주어진 책 상태 ID에 해당하는 책 상태 정보를 삭제합니다.
	 *
	 * @param bookStatusId 삭제할 책 상태 ID
	 * @return 책 상태 리스트 페이지로 리다이렉트
	 */
	@DeleteMapping("/{bookStatusId}")
	public String deleteBookStatus(@PathVariable Long bookStatusId) {
		bookStatusService.deleteBookStatus(bookStatusId);
		return "redirect:/api/bookStatuses";
	}
}
