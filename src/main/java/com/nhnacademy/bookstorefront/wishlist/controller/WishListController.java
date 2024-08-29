package com.nhnacademy.bookstorefront.wishlist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.service.WishListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author 이경헌
 * 위시리스트 관련 기능을 담당하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/wish-lists")
public class WishListController {
	private final WishListService wishListService;

	/**
	 * 모든 위시리스트 목록을 조회합니다.
	 *
	 * @param model 데이터 모델
	 * @return 위시리스트 목록 페이지의 뷰 이름
	 */
	@GetMapping("/me")
	public String getWishLists(Model model) {
		model.addAttribute("wishLists", wishListService.getWishLists());
		return "wishlist/list-wishlist";
	}

	/**
	 * 새로운 위시리스트를 생성합니다.
	 *
	 * @param request 생성할 위시리스트 요청 객체
	 * @return 생성된 위시리스트의 응답 상태
	 */
	@PostMapping
	public ResponseEntity<Void> createWishList(@Valid @RequestBody CreateWishListRequest request) {
		return wishListService.createWishList(request);
	}

	/**
	 * 위시리스트를 삭제합니다.
	 *
	 * @param wishListId 삭제할 위시리스트의 ID
	 * @return 위시리스트 목록 페이지로 리다이렉트하는 URL
	 */
	@DeleteMapping("/{wishListId}")
	public String deleteWishList(@PathVariable Long wishListId) {
		wishListService.deleteWishList(wishListId);
		return "redirect:/wish-lists/me";
	}

}
