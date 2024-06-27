package com.nhnacademy.bookstorefront.wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.service.WishListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishLists")
public class WishListController {
	private final WishListService wishListService;

	@GetMapping
	public String getWishLists(Model model) {
		model.addAttribute("wishLists", wishListService.getWishLists());
		return "wishlist/list-wishlist";
	}

	@PostMapping
	public void createWishList(@ModelAttribute CreateWishListRequest request) {
		wishListService.createWishList(request);
	}

	@DeleteMapping("{wishListId}")
	public String deleteWishList(@PathVariable Long wishListId) {
		wishListService.deleteWishList(wishListId);
		return "redirect:/wishLists";
	}

}
