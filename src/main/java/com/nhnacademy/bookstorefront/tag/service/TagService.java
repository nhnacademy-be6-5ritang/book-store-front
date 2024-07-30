package com.nhnacademy.bookstorefront.tag.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstorefront.tag.dto.response.TagDto;

/**
 * @author 김기욱, 이경헌
 * 카테고리 정보를 관리하는 인터페이스입니다.
 */
public interface TagService {
	/**
	 * 모든 태그 목록을 조회합니다.
	 *
	 * @return 태그 목록
	 */
	List<TagDto> getTags();

	/**
	 * 지정된 페이지 정보에 따른 태그 목록을 조회합니다.
	 *
	 * @param pageable 페이징 정보
	 * @return 페이징된 태그 목록
	 */
	Page<TagDto> getTags(Pageable pageable);

	/**
	 * 지정된 책 ID에 해당하는 태그 목록을 조회합니다.
	 *
	 * @param bookId 책 ID
	 * @return 책에 대한 태그 목록
	 */
	List<TagDto> getTagsByBookId(Long bookId);

	/**
	 * 지정된 태그 ID에 해당하는 태그 정보를 조회합니다.
	 *
	 * @param tagId 태그 ID
	 * @return 태그 정보
	 */
	TagDto getTag(Long tagId);

	/**
	 * 새로운 태그를 생성합니다.
	 *
	 * @param tagDto 생성할 태그 정보
	 * @return 생성된 태그 정보
	 */
	TagDto createTag(TagDto tagDto);

	/**
	 * 지정된 태그 ID에 해당하는 태그 정보를 수정합니다.
	 *
	 * @param tagId   수정할 태그 ID
	 * @param tagDto 수정할 태그 정보
	 * @return 수정된 태그 정보
	 */
	TagDto updateTag(Long tagId, TagDto tagDto);

	/**
	 * 지정된 태그 ID에 해당하는 태그를 삭제합니다.
	 *
	 * @param tagId 삭제할 태그 ID
	 */
	void deleteTag(Long tagId);
}
