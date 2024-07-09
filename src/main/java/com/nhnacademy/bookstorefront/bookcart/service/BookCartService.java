package com.nhnacademy.bookstorefront.bookcart.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;

/**
 * @author 이경헌
 * BookCartService는 도서 장바구니와 관련된 비즈니스 로직을 처리하는 인터페이스입니다.
 */
public interface BookCartService {
	/**
	 * 특정 장바구니의 도서 목록을 조회합니다.
	 *
	 * @param cartId 조회할 장바구니의 ID
	 * @return 도서 목록이 포함된 리스트
	 */
	List<GetBookCartResponse> getBookCartsByCartId(Long cartId);

	/**
	 * 새로운 도서를 장바구니에 추가합니다.
	 *
	 * @param request 추가할 도서의 정보를 담은 CreateBookCartRequest 객체
	 * @param cartId 도서를 추가할 장바구니의 ID
	 * @return 상태 코드가 포함된 ResponseEntity 객체
	 */
	ResponseEntity<Void> createBookCart(CreateBookCartRequest request, Long cartId);

	/**
	 * 특정 도서의 장바구니 정보를 수정합니다.
	 *
	 * @param bookCartId 수정할 도서의 장바구니 ID
	 * @param request 수정할 도서의 정보를 담은 UpdateBookCartRequest 객체
	 * @param cartId 도서가 포함된 장바구니의 ID
	 * @return 상태 코드가 포함된 ResponseEntity 객체
	 */
	ResponseEntity<Void> updateBookCart(Long bookCartId, UpdateBookCartRequest request, Long cartId);

	/**
	 * 특정 도서를 장바구니에서 삭제합니다.
	 *
	 * @param bookCartId 삭제할 도서의 장바구니 ID
	 * @param cartId 도서가 포함된 장바구니의 ID
	 */
	void deleteBookCart(Long bookCartId, Long cartId);
}