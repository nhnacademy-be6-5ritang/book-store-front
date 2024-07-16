package com.nhnacademy.bookstorefront.bookcart.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 이경헌
 * 도서 장바구니와 관련된 비즈니스 로직을 처리하는 인터페이스입니다.
 */
public interface BookCartService {
	/**
	 * 특정 장바구니의 도서 목록을 조회합니다.
	 *
	 * @param cartId 조회할 장바구니의 ID
	 * @param resp   HTTP 응답 객체로, 필요한 경우 쿠키를 설정을 위해 사용
	 * @return 도서 목록이 포함된 리스트
	 */
	List<GetBookCartResponse> getBookCartsByCartId(String cartId, HttpServletResponse resp);

	/**
	 * 새로운 도서를 장바구니에 추가합니다.
	 *
	 * @param request 추가할 도서의 정보를 담은 CreateBookCartRequest 객체
	 * @param cartId 도서를 추가할 장바구니의 ID
	 * @return 상태 코드가 포함된 ResponseEntity 객체
	 */
	ResponseEntity<Void> createBookCart(CreateBookCartRequest request, String cartId);

	/**
	 * 특정 도서의 장바구니 정보를 수정합니다.
	 *
	 * @param bookId 수정할 도서의 ID
	 * @param request 수정할 도서의 정보를 담은 UpdateBookCartRequest 객체
	 * @param cartId 도서가 포함된 장바구니의 ID
	 * @return 상태 코드가 포함된 ResponseEntity 객체
	 */
	ResponseEntity<Void> updateBookCart(Long bookId, UpdateBookCartRequest request, String cartId);

	/**
	 * 특정 도서를 장바구니에서 삭제합니다.
	 *
	 * @param bookId 삭제할 도서의 ID
	 * @param cartId 도서가 포함된 장바구니의 ID
	 */
	ResponseEntity<Void> deleteBookCart(Long bookId, String cartId);
}