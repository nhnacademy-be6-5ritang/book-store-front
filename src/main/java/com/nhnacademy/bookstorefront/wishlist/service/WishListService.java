package com.nhnacademy.bookstorefront.wishlist.service;

import java.util.List;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.response.CreateWishListResponse;
import com.nhnacademy.bookstorefront.wishlist.dto.response.GetWishListResponse;

public interface WishListService {
	List<GetWishListResponse> getWishLists();

	CreateWishListResponse createWishList(CreateWishListRequest request);

	void deleteWishList(Long wishListId);
}
