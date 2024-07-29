package com.nhnacademy.bookstorefront.book.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;

/**
 * 외부 Book 서비스와 통신하여 책 정보를 관리하는 인터페이스입니다.
 *
 *
 * @version 1.0
 */
public interface BookService {

	/**
	 * 주어진 책 ID에 해당하는 책의 상세 정보를 조회합니다.
	 *
	 * @param bookId 책 ID
	 * @return 책의 상세 정보
	 */
	GetBookDetailResponse getBook(Long bookId);

	/**
	 * 모든 책의 상세 정보를 조회합니다.
	 *
	 * @return 모든 책의 상세 정보 목록
	 */
	List<GetBookDetailResponse> getNewestBooks();

	/**
	 * 모든 책의 상세 정보를 페이지네이션하여 조회합니다.
	 *
	 * @param pageable 페이지 정보
	 * @return 페이지네이션된 책의 상세 정보 목록
	 */
	Page<GetBookDetailResponse> getNewestBooks(Pageable pageable);

	/**
	 * 새로운 책을 생성합니다.
	 *
	 * @param request 생성할 책 정보 DTO
	 */
	void createBook(CreateBookRequest request, MultipartFile file);

	/**
	 * 주어진 책 ID에 해당하는 책을 업데이트합니다.
	 *
	 * @param bookId  업데이트할 책 ID
	 * @param request 업데이트할 책 정보 DTO
	 */
	void updateBookById(Long bookId, UpdateBookRequest request, MultipartFile file);

	/**
	 * ISBN 을 통해 도서 정보를 가져와 저장하는 메서드입니다.
	 *
	 * @param isbn 가져와 저장할 도서의 ISBN
	 */
	void saveBookByIsbn(String isbn);

	/**
	 * 주어진 책 ID에 해당하는 책을 삭제합니다.
	 *
	 * @param bookId 삭제할 책 ID
	 */
	void deleteBook(Long bookId);

	List<BookSearchResult> searchBooks(String query);

	List<GetBookDetailResponse> getOrderedBooks();

	List<GetBookDetailResponse> getLikesBooks();
}
