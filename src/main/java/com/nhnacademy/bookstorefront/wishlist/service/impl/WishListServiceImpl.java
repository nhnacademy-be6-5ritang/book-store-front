package com.nhnacademy.bookstorefront.wishlist.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
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
	public List<GetWishListResponse> getWishLists() {
		return wishListServiceClient.getWishLists().getBody();
	}

	@Override
	public CreateWishListResponse createWishList(CreateWishListRequest request) {
		return wishListServiceClient.createWishList(request).getBody();
	}

	@Override
	public void deleteWishList(Long wishListId) {
		wishListServiceClient.deleteWishList(wishListId);
	}
}
