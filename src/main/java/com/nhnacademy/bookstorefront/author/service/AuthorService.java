package com.nhnacademy.bookstorefront.author.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;

/**
 * @author 김기욱, 이경헌
 * 외부 Author 서비스와 통신하여 작가 정보를 관리하는 인터페이스입니다.
 */
public interface AuthorService {

	/**
	 * 지정된 페이지 정보에 따른 저자 목록을 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @return 페이징된 저자 목록
	 */
	Page<AuthorDto> getAuthors(Pageable pageable);

	/**
	 * 주어진 작가 ID에 해당하는 작가 정보를 조회합니다.
	 *
	 * @param authorId 작가 ID
	 * @return 작가 정보
	 */
	AuthorDto getAuthor(Long authorId);

	/**
	 * 새로운 작가 정보를 생성합니다.
	 *
	 * @param authorDto 생성할 작가 정보 DTO
	 */
	void createAuthor(AuthorDto authorDto);

	/**
	 * 주어진 작가 ID에 해당하는 작가 정보를 수정합니다.
	 *
	 * @param authorId  수정할 작가 ID
	 * @param authorDto 수정할 작가 정보 DTO
	 */
	void updateAuthor(Long authorId, AuthorDto authorDto);

	/**
	 * 주어진 작가 ID에 해당하는 작가 정보를 삭제합니다.
	 *
	 * @param authorId 삭제할 작가 ID
	 */
	void deleteAuthor(Long authorId);
}
