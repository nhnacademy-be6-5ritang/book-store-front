package com.nhnacademy.bookstorefront.author.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;
import com.nhnacademy.bookstorefront.author.feignclient.AuthorServiceClient;
import com.nhnacademy.bookstorefront.author.service.AuthorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
	private final AuthorServiceClient authorServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<AuthorDto> getAuthors() {
		return authorServiceClient.getAuthors().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<AuthorDto> getAuthors(Pageable pageable) {
		return authorServiceClient.getAuthors(pageable).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public AuthorDto getAuthor(Long authorId) {
		return authorServiceClient.getAuthor(authorId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void createAuthor(AuthorDto authorDto) {
		authorServiceClient.createAuthor(authorDto);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void updateAuthor(Long authorId, AuthorDto authorDto) {
		authorServiceClient.updateAuthor(authorId, authorDto);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void deleteAuthor(Long authorId) {
		authorServiceClient.deleteAuthor(authorId);
	}
}
