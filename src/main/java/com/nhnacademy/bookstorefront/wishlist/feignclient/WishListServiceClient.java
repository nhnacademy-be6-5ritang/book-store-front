package com.nhnacademy.bookstorefront.wishlist.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.response.CreateWishListResponse;
import com.nhnacademy.bookstorefront.wishlist.dto.response.GetWishListResponse;

@FeignClient(name = "wishList-service", url = "http://localhost:8083")
public interface WishListServiceClient {

	@GetMapping("/wishLists")
	ResponseEntity<List<GetWishListResponse>> getWishLists();

	@PostMapping("/wishLists")
	ResponseEntity<CreateWishListResponse> createWishList(@RequestBody CreateWishListRequest request);

	@DeleteMapping("/wishLists/{wishListId}")
	ResponseEntity<Void> deleteWishList(@PathVariable Long wishListId);
}
