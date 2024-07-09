package com.nhnacademy.bookstorefront.wishlist.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.response.GetWishListResponse;

public interface WishListService {
	List<GetWishListResponse> getWishLists();

	ResponseEntity<Void> createWishList(CreateWishListRequest request);

	void deleteWishList(Long wishListId);
}
