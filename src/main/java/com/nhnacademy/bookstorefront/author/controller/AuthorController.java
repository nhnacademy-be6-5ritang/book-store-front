package com.nhnacademy.bookstorefront.author.controller;

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

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;
import com.nhnacademy.bookstorefront.author.service.impl.AuthorServiceImpl;
import com.nhnacademy.bookstorefront.global.util.PagingModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author 김기욱, 이경헌
 * 저자 관리 웹 페이지 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
	private final AuthorServiceImpl authorService;
	private static final String REDIRECT_URL = "redirect:/api/authors/page";

	/**
	 * 저자 생성 폼을 반환합니다.
	 *
	 * @return 저자 생성 폼 뷰 이름
	 */
	@GetMapping("/create")
	public String createAuthorForm() {
		return "author/create-author";
	}

	/**
	 * 주어진 저자 ID에 해당하는 저자 정보를 수정하는 폼을 반환합니다.
	 *
	 * @param authorId 수정할 저자 ID
	 * @param model    모델 객체
	 * @return 저자 수정 폼 뷰 이름
	 */
	@GetMapping("/update/{authorId}")
	public String updateAuthorForm(@PathVariable Long authorId, Model model) {
		model.addAttribute("author", authorService.getAuthor(authorId));
		return "author/update-author";
	}

	/**
	 * 모든 저자 정보를 조회하여 리스트로 반환합니다.
	 *
	 * @param model 모델 객체
	 * @return 저자 리스트 뷰 이름
	 */
	@GetMapping
	public String getAuthors(Model model) {
		model.addAttribute("authors", authorService.getAuthors());
		return "author/list-author";
	}

	/**
	 * 페이지네이션을 포함하여 모든 저자 정보를 조회합니다.
	 *
	 * @param pageable 페이지네이션 정보
	 * @param model    모델 객체
	 * @return 저자 리스트 뷰 이름
	 */
	@GetMapping("/page")
	public String getAuthors(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model) {
		Page<AuthorDto> authors = authorService.getAuthors(pageable);
		model.addAttribute("authors", authors);
		PagingModel.pagingProcessing(pageable, model, authors, "/api/authors/page", 5);

		return "author/list-author";
	}

	/**
	 * 새로운 저자 정보를 생성합니다.
	 *
	 * @param request 생성할 저자 정보 DTO
	 * @return 저자 리스트 페이지로 리다이렉트
	 */
	@PostMapping
	public String createAuthor(@Valid @ModelAttribute AuthorDto request) {
		authorService.createAuthor(request);
		return REDIRECT_URL;
	}

	/**
	 * 주어진 저자 ID에 해당하는 저자 정보를 수정합니다.
	 *
	 * @param authorId 수정할 저자 ID
	 * @param request  수정할 저자 정보 DTO
	 * @return 저자 리스트 페이지로 리다이렉트
	 */
	@PutMapping("/{authorId}")
	public String updateAuthor(@PathVariable Long authorId, @Valid @ModelAttribute AuthorDto request) {
		authorService.updateAuthor(authorId, request);
		return REDIRECT_URL;
	}

	/**
	 * 주어진 저자 ID에 해당하는 저자 정보를 삭제합니다.
	 *
	 * @param authorId 삭제할 저자 ID
	 * @return 저자 리스트 페이지로 리다이렉트
	 */
	@DeleteMapping("/{authorId}")
	public String deleteAuthor(@PathVariable Long authorId) {
		authorService.deleteAuthor(authorId);
		return REDIRECT_URL;
	}
}
