package com.nhnacademy.bookstorefront.wishlist.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.request.GetWishListsRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.response.CreateWishListResponse;
import com.nhnacademy.bookstorefront.wishlist.dto.response.GetWishListResponse;

public interface WishListService {
	ResponseEntity<List<GetWishListResponse>> getWishLists(GetWishListsRequest request);

	ResponseEntity<CreateWishListResponse> createWishList(CreateWishListRequest request);

	ResponseEntity<Void> deleteWishList(Long wishListId);
}
