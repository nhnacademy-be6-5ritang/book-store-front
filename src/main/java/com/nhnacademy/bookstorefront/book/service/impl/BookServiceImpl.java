package com.nhnacademy.bookstorefront.book.service.impl;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.feignclient.BookServiceClient;
import com.nhnacademy.bookstorefront.book.service.BookService;
import com.nhnacademy.bookstorefront.upload.feignclient.UploadServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookServiceClient bookServiceClient;
    private final UploadServiceClient uploadServiceClient;

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "books", key = "#bookId")
    public GetBookDetailResponse getBook(Long bookId) {
        return bookServiceClient.getBook(bookId).getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GetBookDetailResponse> getBooks(Pageable pageable) {
        return bookServiceClient.getBooks(pageable).getBody();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void createBook(CreateBookRequest request, MultipartFile file) {
        String fileName = null;
        if (!file.isEmpty()) {
            fileName = uploadServiceClient.upload(file, "books").getBody();
        }

        bookServiceClient.createBook(CreateBookRequest.from(request, fileName));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBookById(Long bookId, UpdateBookRequest request, MultipartFile file) {
        String fileName = null;
        if (!file.isEmpty()) {
            fileName = uploadServiceClient.upload(file, "books").getBody();
        }

        bookServiceClient.updateBookById(bookId, UpdateBookRequest.from(request, fileName));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveBookByIsbn(String isbn) {
        bookServiceClient.saveBookByIsbn(isbn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBook(Long bookId) {
        bookServiceClient.deleteBook(bookId);
    }

    public void updateQuantity(Long bookId, int quantity) {
        bookServiceClient.updateQuantity(bookId, quantity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookSearchResult> searchBooks(String query) {
        return bookServiceClient.searchBooks(query).getBody();
    }
}
