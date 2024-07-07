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

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/wishLists")
public class WishListController {
	private final WishListService wishListService;

	@GetMapping
	public String getWishLists(Model model) {
		model.addAttribute("wishLists", wishListService.getWishLists());
		return "wishlist/list-wishlist";
	}

	@PostMapping
	public ResponseEntity<Void> createWishList(@RequestBody CreateWishListRequest request) {
		return wishListService.createWishList(request);

		// try {
		// 	return wishListService.createWishList(request);
		// } catch (Exception e) {
		// 	if (e.getMessage().contains("409")) {
		// 		return ResponseEntity.status(HttpStatus.CONFLICT).build();
		// 	} else {
		// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		// 	}
		// }
	}

	@DeleteMapping("/{wishListId}")
	public String deleteWishList(@PathVariable Long wishListId) {
		wishListService.deleteWishList(wishListId);
		return "redirect:/api/wishLists";
	}

}
