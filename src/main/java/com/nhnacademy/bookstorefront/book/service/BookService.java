package com.nhnacademy.bookstorefront.book.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookResponse;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookTitleResponse;

/**
 * @author 김기욱, 이경헌
 * 외부 Book 서비스와 통신하여 도서 정보를 관리하는 인터페이스입니다.
 */
public interface BookService {

	/**
	 * 모든 도서의 상세 정보를 페이지네이션하여 조회합니다.
	 *
	 * @param pageable 페이지 정보
	 * @return 페이지네이션된 도서의 상세 정보 목록
	 */
	Page<GetBookTitleResponse> getBooks(Pageable pageable);

	/**
	 * 주어진 도서 ID에 해당하는 도서의 상세정보를 조회합니다.
	 *
	 * @param bookId 도서 ID
	 * @return 도서의 상세 정보
	 */
	GetBookResponse getBook(Long bookId);

	/**
	 * 새로운 도서를 생성합니다.
	 *
	 * @param request 생성할 도서 정보 DTO
	 */
	void createBook(CreateBookRequest request, MultipartFile file);

	/**
	 * 주어진 도서 ID에 해당하는 도서를 업데이트합니다.
	 *
	 * @param bookId  업데이트할 도서 ID
	 * @param request 업데이트할 도서 정보 DTO
	 */
	void updateBookById(Long bookId, UpdateBookRequest request, MultipartFile file);

	/**
	 * ISBN 을 통해 도서 정보를 가져와 저장하는 메서드입니다.
	 *
	 * @param isbn 가져와 저장할 도서의 ISBN
	 */
	void saveBookByIsbn(String isbn);

	/**
	 * 주어진 도서 ID에 해당하는 도서을 삭제합니다.
	 *
	 * @param bookId 삭제할 도서 ID
	 */
	void deleteBook(Long bookId);

	/**
	 * 도서의 수량을 업데이트합니다.
	 *
	 * @param bookId 도서의 고유 식별자
	 * @param quantity 업데이트할 도서의 새로운 수량
	 */
	void updateQuantity(Long bookId, int quantity);

	/**
	 * 주어진 쿼리 문자열을 기반으로 도서를 검색합니다.
	 *
	 * @param query 검색할 문자열
	 * @return 검색된 도서 목록이 포함된 {@link List<BookSearchResult>} 객체
	 */
	List<BookSearchResult> searchBooks(String query);
}
