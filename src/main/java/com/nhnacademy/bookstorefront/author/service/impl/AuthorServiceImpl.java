package com.nhnacademy.bookstorefront.author.service.impl;

import java.util.List;

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
	public AuthorDto getAuthor(Long authorId) {
		return authorServiceClient.getAuthor(authorId).getBody();
	}

	@Override
	public AuthorDto createAuthor(AuthorDto authorDto) {
		return authorServiceClient.createAuthor(authorDto).getBody();
	}

	@Override
	public AuthorDto updateAuthor(Long authorId, AuthorDto authorDto) {
		return authorServiceClient.updateAuthor(authorId, authorDto).getBody();
	}

	@Override
	public void deleteAuthor(Long authorId) {
		authorServiceClient.deleteAuthor(authorId);
	}
}
