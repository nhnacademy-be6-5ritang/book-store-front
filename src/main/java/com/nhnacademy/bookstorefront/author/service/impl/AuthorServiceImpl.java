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

	@Override
	public List<AuthorDto> getAuthors() {
		return authorServiceClient.getAuthors().getBody();
	}

	@Override
	public Page<AuthorDto> getAuthors(Pageable pageable) {
		return authorServiceClient.getAuthors(pageable).getBody();
	}

	@Override
	public AuthorDto getAuthor(Long authorId) {
		return authorServiceClient.getAuthor(authorId).getBody();
	}

	@Override
	public void createAuthor(AuthorDto authorDto) {
		authorServiceClient.createAuthor(authorDto);
	}

	@Override
	public void updateAuthor(Long authorId, AuthorDto authorDto) {
		authorServiceClient.updateAuthor(authorId, authorDto);
	}

	@Override
	public void deleteAuthor(Long authorId) {
		authorServiceClient.deleteAuthor(authorId);
	}
}
