package com.nhnacademy.bookstorefront.tag.controller;

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

import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;
import com.nhnacademy.bookstorefront.tag.service.TagService;

import lombok.RequiredArgsConstructor;

/**
 * 태그 관리 웹 페이지 컨트롤러입니다.
 * 이 컨트롤러는 태그 정보를 생성, 조회, 수정, 삭제하는 기능을 제공합니다.
 * 또한 웹 페이지에서 사용할 뷰를 반환합니다.
 *
 * <p>태그 정보를 조회하는 메서드에서는 태그 목록을 뷰로 반환합니다.
 * 태그 생성 및 수정 시 해당 폼을 뷰로 반환합니다.
 * 삭제 시 해당 태그를 삭제하고 태그 목록 페이지로 리다이렉트합니다.
 *
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
	private final TagService tagService;

	/**
	 * 새로운 태그 생성 폼을 반환합니다.
	 *
	 * @return 태그 생성 폼 뷰 이름
	 */
	@GetMapping("/create")
	public String createTagForm() {
		return "tag/create-tag";
	}

	/**
	 * 주어진 태그 ID에 해당하는 태그 정보를 수정하는 폼을 반환합니다.
	 *
	 * @param tagId 태그 ID
	 * @param model 모델 객체
	 * @return 태그 수정 폼 뷰 이름
	 */
	@GetMapping("/update/{tagId}")
	public String updateTagForm(@PathVariable Long tagId, Model model) {
		model.addAttribute("tag", tagService.getTag(tagId));
		return "tag/update-tag";
	}

	/**
	 * 모든 태그 정보를 조회합니다.
	 *
	 * @param model 모델 객체
	 * @return 태그 리스트 뷰 이름
	 */
	@GetMapping
	public String getTags(Model model) {
		model.addAttribute("tags", tagService.getTags());
		return "tag/list-tag";
	}

	/**
	 * 페이지네이션을 포함하여 모든 태그 정보를 조회합니다.
	 *
	 * @param pageable 페이지네이션 정보
	 * @param model    모델 객체
	 * @return 태그 리스트 뷰 이름
	 */
	@GetMapping("/page")
	public String getTags(@PageableDefault(page = 1) Pageable pageable, Model model) {
		Page<TagDto> tags = tagService.getTags(pageable);
		model.addAttribute("tags", tags);
		model.addAttribute("objects", tags); // 공통 객체 이름
		model.addAttribute("baseUrl", "/api/tags/page"); // 페이징 URL

		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
		int endPage = Math.min((startPage + blockLimit - 1), tags.getTotalPages());

		model.addAttribute("pageable", pageable);
		model.addAttribute("blockLimit", blockLimit);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "tag/list-tag";
	}

	/**
	 * 새로운 태그 정보를 생성합니다.
	 *
	 * @param request 생성할 태그 정보 DTO
	 * @return 태그 리스트 페이지로 리다이렉트
	 */
	@PostMapping
	public String createTag(@ModelAttribute TagDto request) {
		tagService.createTag(request);
		return "redirect:/api/tags/page";
	}

	/**
	 * 주어진 태그 ID에 해당하는 태그 정보를 수정합니다.
	 *
	 * @param tagId   태그 ID
	 * @param request 수정할 태그 정보 DTO
	 * @return 태그 리스트 페이지로 리다이렉트
	 */
	@PutMapping("/{tagId}")
	public String updateTag(@PathVariable Long tagId, @ModelAttribute TagDto request) {
		tagService.updateTag(tagId, request);
		return "redirect:/api/tags/page";
	}

	/**
	 * 주어진 태그 ID에 해당하는 태그 정보를 삭제합니다.
	 *
	 * @param tagId 삭제할 태그 ID
	 * @return 태그 리스트 페이지로 리다이렉트
	 */
	@DeleteMapping("/{tagId}")
	public String deleteTag(@PathVariable Long tagId) {
		tagService.deleteTag(tagId);
		return "redirect:/api/tags/page";
	}
}
