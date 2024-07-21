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

import com.nhnacademy.bookstorefront.global.util.PagingModel;
import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;
import com.nhnacademy.bookstorefront.tag.service.impl.TagServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌
 * 태그 관리 웹 페이지 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
	private final TagServiceImpl tagService;
	private static final String REDIRECT_URL = "redirect:/api/tags/page";

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
	public String getTags(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model) {
		Page<TagDto> tags = tagService.getTags(pageable);
		model.addAttribute("tags", tags);
		PagingModel.pagingProcessing(pageable, model, tags, "/api/tags/page", 5);
		return "tag/list-tag";
	}

	/**
	 * 새로운 태그 정보를 생성합니다.
	 *
	 * @param request 생성할 태그 정보 DTO
	 * @return 태그 리스트 페이지로 리다이렉트
	 */
	@PostMapping
	public String createTag(@Valid @ModelAttribute TagDto request) {
		tagService.createTag(request);
		return REDIRECT_URL;
	}

	/**
	 * 주어진 태그 ID에 해당하는 태그 정보를 수정합니다.
	 *
	 * @param tagId   태그 ID
	 * @param request 수정할 태그 정보 DTO
	 * @return 태그 리스트 페이지로 리다이렉트
	 */
	@PutMapping("/{tagId}")
	public String updateTag(@PathVariable Long tagId, @Valid @ModelAttribute TagDto request) {
		tagService.updateTag(tagId, request);
		return REDIRECT_URL;
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
		return REDIRECT_URL;
	}
}
