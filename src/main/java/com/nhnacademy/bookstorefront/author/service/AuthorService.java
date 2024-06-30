package com.nhnacademy.bookstorefront.author.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.author.dto.response.AuthorDto;
import com.nhnacademy.bookstorefront.author.feignclient.AuthorServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
	private final AuthorServiceClient authorServiceClient;

	public List<AuthorDto> getAuthors() {
		return authorServiceClient.getAuthors().getBody();
	}

	public AuthorDto getAuthor(Long authorId) {
		return authorServiceClient.getAuthor(authorId).getBody();
	}

	public AuthorDto createAuthor(AuthorDto authorDto) {
		return authorServiceClient.createAuthor(authorDto).getBody();
	}

	public AuthorDto updateAuthor(Long authorId, AuthorDto authorDto) {
		return authorServiceClient.updateAuthor(authorId, authorDto).getBody();
	}

	public void deleteAuthor(Long authorId) {
		authorServiceClient.deleteAuthor(authorId);
	}
}
