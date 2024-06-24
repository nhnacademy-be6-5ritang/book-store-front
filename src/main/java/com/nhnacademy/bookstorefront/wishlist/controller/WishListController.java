package com.nhnacademy.bookstorefront.wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.request.GetWishListsRequest;
import com.nhnacademy.bookstorefront.wishlist.service.WishListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishLists")
public class WishListController {
	private final WishListService wishListService;

	@GetMapping
	public ModelAndView getWishLists(@RequestBody GetWishListsRequest request) {
		ModelAndView modelAndView = new ModelAndView("wishlist/list-wishlist");
		modelAndView.addObject("wishLists", wishListService.getWishLists(request));
		return modelAndView;
	}

	@GetMapping("/view")
	public ModelAndView getWishLists() {
		return new ModelAndView("wishlist/list-wishlist");
	}

	@PostMapping
	public String createWishList(@RequestBody CreateWishListRequest request) {
		wishListService.createWishList(request);
		return "redirect:/wishLists";
	}

	@DeleteMapping("{wishListId}")
	public String deleteWishList(@PathVariable Long wishListId) {
		wishListService.deleteWishList(wishListId);
		return "redirect:/wishLists";
	}

}
