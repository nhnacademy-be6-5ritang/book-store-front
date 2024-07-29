package com.nhnacademy.bookstorefront.book.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.feignclient.BookServiceClient;
import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.upload.feignclient.UploadServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookServiceClient bookServiceClient;
	private final UploadServiceClient uploadServiceClient;

	@Override
	public GetBookDetailResponse getBook(Long bookId) {
		return bookServiceClient.getBook(bookId).getBody();
	}

	@Override
	public List<GetBookDetailResponse> getNewestBooks() {
		return bookServiceClient.getNewestBooks().getBody();
	}

	@Override
	public List<GetBookDetailResponse> getOrderedBooks() {
		return bookServiceClient.getOrderedBooks().getBody();
	}

	@Override
	public List<GetBookDetailResponse> getLikesBooks() {
		return bookServiceClient.getLikesBooks().getBody();
	}

	@Override
	public Page<GetBookDetailResponse> getNewestBooks(Pageable pageable) {
		return bookServiceClient.getNewestBooks(pageable).getBody();
	}

	public Page<GetBookDetailResponse> findAllBooksByCategory(Pageable pageable, String categoryName) {
		return bookServiceClient.findAllBooksByCategoryName(pageable, categoryName).getBody();
	}

	@Override
	public void createBook(CreateBookRequest request, MultipartFile file) {
		String fileName = null;
		if (!file.isEmpty()) {
			fileName = uploadServiceClient.upload(file).getBody();
		}

		bookServiceClient.createBook(CreateBookRequest.from(request, fileName));
	}

	@Override
	public void updateBookById(Long bookId, UpdateBookRequest request, MultipartFile file) {
		String fileName = null;
		if (!file.isEmpty()) {
			fileName = uploadServiceClient.upload(file).getBody();
		}

		bookServiceClient.updateBookById(bookId, UpdateBookRequest.from(request, fileName));
	}

	@Override
	public void saveBookByIsbn(String isbn) {
		bookServiceClient.saveBookByIsbn(isbn);
	}

	@Override
	public void deleteBook(Long bookId) {
		bookServiceClient.deleteBook(bookId);
	}

	public void updateQuantity(Long bookId, int quantity) {
		bookServiceClient.updateQuantity(bookId, quantity);
	}

	@Override
	public List<BookSearchResult> searchBooks(String query) {
		return bookServiceClient.searchBooks(query).getBody();
	}
}
