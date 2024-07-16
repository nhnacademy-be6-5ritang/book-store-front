package com.nhnacademy.bookstorefront.publisher.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.publisher.dto.response.PublisherDto;
import com.nhnacademy.bookstorefront.publisher.service.impl.PublisherServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 출판사 관리 웹 페이지 컨트롤러입니다.
 * 이 컨트롤러는 출판사 정보를 생성, 조회, 수정, 삭제하는 기능을 제공합니다.
 * 또한 웹 페이지에서 사용할 뷰를 반환합니다.
 *
 * <p>출판사 정보를 조회하는 메서드에서는 출판사 목록을 뷰로 반환합니다.
 * 출판사 생성 및 수정 시 해당 폼을 뷰로 반환합니다.
 * 삭제 시 해당 출판사를 삭제하고 출판사 목록 페이지로 리다이렉트합니다.
 *
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/publishers")
public class PublisherController {
	private final PublisherServiceImpl publisherService;

	/**
	 * 새로운 출판사 생성 폼을 반환합니다.
	 *
	 * @return 출판사 생성 폼 뷰 이름
	 */
	@GetMapping("/create")
	public String createPublisherForm() {
		return "publisher/create-publisher";
	}

	/**
	 * 주어진 출판사 ID에 해당하는 출판사 정보를 수정하는 폼을 반환합니다.
	 *
	 * @param publisherId 출판사 ID
	 * @param model       모델 객체
	 * @return 출판사 수정 폼 뷰 이름
	 */
	@GetMapping("/update/{publisherId}")
	public String updatePublisherForm(@PathVariable Long publisherId, Model model) {
		model.addAttribute("publisher", publisherService.getPublisher(publisherId));
		return "publisher/update-publisher";
	}

	/**
	 * 모든 출판사 정보를 조회합니다.
	 *
	 * @param model 모델 객체
	 * @return 출판사 리스트 뷰 이름
	 */
	@GetMapping
	public String getPublishers(Model model) {
		model.addAttribute("publishers", publisherService.getPublishers());
		return "publisher/list-publisher";
	}

	/**
	 * 페이지네이션을 포함하여 모든 출판사 정보를 조회합니다.
	 *
	 * @param pageable 페이지네이션 정보
	 * @param model    모델 객체
	 * @return 출판사 리스트 뷰 이름
	 */
	@GetMapping("page")
	public String getPublishers(@PageableDefault(page = 1) Pageable pageable, Model model) {
		Page<PublisherDto> publishers = publisherService.getPublishers(pageable);
		model.addAttribute("publishers", publishers);

		PagingModel.pagingProcessing(pageable, model, publishers, "/api/publishers/page", 5);

		return "publisher/list-publisher";
	}

	/**
	 * 새로운 출판사 정보를 생성합니다.
	 *
	 * @param request 생성할 출판사 정보 DTO
	 * @return 출판사 리스트 페이지로 리다이렉트
	 */
	@PostMapping
	public String createPublisher(@Valid @ModelAttribute PublisherDto request) {
		publisherService.createPublisher(request);
		return "redirect:/api/publishers/page";
	}

	/**
	 * 주어진 출판사 ID에 해당하는 출판사 정보를 수정합니다.
	 *
	 * @param publisherId 출판사 ID
	 * @param request     수정할 출판사 정보 DTO
	 * @return 출판사 리스트 페이지로 리다이렉트
	 */
	@PutMapping("/{publisherId}")
	public String updatePublisher(@PathVariable Long publisherId, @Valid @ModelAttribute PublisherDto request) {
		publisherService.updatePublisher(publisherId, request);
		return "redirect:/api/publishers/page";
	}

	/**
	 * 주어진 출판사 ID에 해당하는 출판사 정보를 삭제합니다.
	 *
	 * @param publisherId 삭제할 출판사 ID
	 * @return 출판사 리스트 페이지로 리다이렉트
	 */
	@DeleteMapping("/{publisherId}")
	public String deletePublisher(@PathVariable Long publisherId) {
		publisherService.deletePublisher(publisherId);
		return "redirect:/api/publishers/page";
	}
}
