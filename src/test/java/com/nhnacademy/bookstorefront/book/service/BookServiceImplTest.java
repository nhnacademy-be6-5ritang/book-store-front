package com.nhnacademy.bookstorefront.book.service;

import com.nhnacademy.bookstorefront.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.request.UpdateBookRequest;
import com.nhnacademy.bookstorefront.book.dto.response.BookSearchResult;
import com.nhnacademy.bookstorefront.book.dto.response.GetBookDetailResponse;
import com.nhnacademy.bookstorefront.book.feignclient.BookServiceClient;
import com.nhnacademy.bookstorefront.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstorefront.upload.feignclient.UploadServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookServiceClient bookServiceClient;

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private UploadServiceClient uploadServiceClient;

    private String folderName = "books";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBook() {
        Long bookId = 1L;
        GetBookDetailResponse expectedBook = new GetBookDetailResponse(
                bookId, "Author", "Publisher", "Status", "Title", "Description", 10,
                new Date(), "ISBN", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, "URL"
        );

        when(bookServiceClient.getBook(bookId)).thenReturn(ResponseEntity.ok(expectedBook));

        GetBookDetailResponse actualBook = bookService.getBook(bookId);

        assertEquals(expectedBook, actualBook);
    }

    @Test
    void testCreateBook_withFile() {
        CreateBookRequest request = new CreateBookRequest(
                "1234567890", List.of(1L), List.of(2L), "Title", "Author", "Publisher",
                new Date(), "Status", "Description", 10, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, null);
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(uploadServiceClient.upload(file, folderName)).thenReturn(ResponseEntity.ok("file-name"));

        bookService.createBook(request, file);

        verify(uploadServiceClient).upload(file, folderName);
        verify(bookServiceClient).createBook(CreateBookRequest.from(request, "file-name"));
    }

    @Test
    void testCreateBook_withoutFile() {
        CreateBookRequest request = new CreateBookRequest(
                "1234567890", List.of(1L), List.of(2L), "Title", "Author", "Publisher",
                new Date(), "Status", "Description", 10, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, null);
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        bookService.createBook(request, file);

        verify(uploadServiceClient, never()).upload(file, folderName);
        verify(bookServiceClient).createBook(CreateBookRequest.from(request, null));
    }

    @Test
    void testUpdateBookById_withFile() {
        Long bookId = 1L;
        UpdateBookRequest request = new UpdateBookRequest(
                "1234567890", List.of(1L), List.of(2L), "Title", "Author", "Publisher",
                new Date(), "Status", "Description", 10, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, null
        );
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(uploadServiceClient.upload(file, folderName)).thenReturn(ResponseEntity.ok("file-name"));

        bookService.updateBookById(bookId, request, file);

        verify(uploadServiceClient).upload(file, folderName);
        verify(bookServiceClient).updateBookById(bookId, UpdateBookRequest.from(request, "file-name"));
    }

    @Test
    void testUpdateBookById_withoutFile() {
        Long bookId = 1L;
        UpdateBookRequest request = new UpdateBookRequest(
                "1234567890", List.of(1L), List.of(2L), "Title", "Author", "Publisher",
                new Date(), "Status", "Description", 10, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, null
        );
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        bookService.updateBookById(bookId, request, file);

        verify(uploadServiceClient, never()).upload(file, folderName);
        verify(bookServiceClient).updateBookById(bookId, UpdateBookRequest.from(request, null));
    }

    @Test
    void testFetchAndSaveBook() {
        String isbn = "1234567890";

        bookService.saveBookByIsbn(isbn);

        verify(bookServiceClient).saveBookByIsbn(isbn);
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;

        bookService.deleteBook(bookId);

        verify(bookServiceClient).deleteBook(bookId);
    }

    @Test
    void testUpdateQuantity() {
        Long bookId = 1L;
        int quantity = 10;

        bookService.updateQuantity(bookId, quantity);

        verify(bookServiceClient).updateQuantity(bookId, quantity);
    }

    @Test
    void testSearchBooks() {
        String query = "searchTerm";
        List<BookSearchResult> expectedResults = List.of(
                new BookSearchResult(1L, "Title1"),
                new BookSearchResult(2L, "Title2")
        );

        when(bookServiceClient.searchBooks(query)).thenReturn(ResponseEntity.ok(expectedResults));

        List<BookSearchResult> actualResults = bookService.searchBooks(query);

        assertEquals(expectedResults, actualResults);
    }
}
