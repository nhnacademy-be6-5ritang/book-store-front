package com.nhnacademy.bookstorefront.wishlist.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.request.GetWishListsRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.response.CreateWishListResponse;
import com.nhnacademy.bookstorefront.wishlist.dto.response.GetWishListResponse;
import com.nhnacademy.bookstorefront.wishlist.feignclient.WishListServiceClient;
import com.nhnacademy.bookstorefront.wishlist.service.WishListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
	private final WishListServiceClient wishListServiceClient;

	@Override
	public ResponseEntity<List<GetWishListResponse>> getWishLists(GetWishListsRequest request) {
		return wishListServiceClient.getWishLists(request);
	}

	@Override
	public ResponseEntity<CreateWishListResponse> createWishList(CreateWishListRequest request) {
		return wishListServiceClient.createWishList(request);
	}

	@Override
	public ResponseEntity<Void> deleteWishList(Long wishListId) {
		return wishListServiceClient.deleteWishList(wishListId);
	}
}
