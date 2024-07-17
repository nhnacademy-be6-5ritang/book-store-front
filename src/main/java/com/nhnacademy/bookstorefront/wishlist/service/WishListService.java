package com.nhnacademy.bookstorefront.wishlist.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.dto.response.GetWishListResponse;

/**
 * @author 이경헌
 * 위시리스트 관련 기능을 제공하는 서비스 인터페이스입니다.
 */
public interface WishListService {
	/**
	 * 모든 위시리스트 목록을 조회합니다.
	 *
	 * @return 모든 위시리스트 목록
	 */
	List<GetWishListResponse> getWishLists();

	/**
	 * 새로운 위시리스트를 생성합니다.
	 *
	 * @param request 생성할 위시리스트 요청 객체
	 * @return 생성된 위시리스트의 응답 상태
	 */
	ResponseEntity<Void> createWishList(CreateWishListRequest request);

	/**
	 * 주어진 위시리스트 ID에 해당하는 위시리스트를 삭제합니다.
	 *
	 * @param wishListId 삭제할 위시리스트의 ID
	 */
	void deleteWishList(Long wishListId);
}
