package com.nhnacademy.bookstorefront.wishlist.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.nhnacademy.bookstorefront.wishlist.dto.request.CreateWishListRequest;
import com.nhnacademy.bookstorefront.wishlist.service.WishListService;

class WishListControllerTest {

	@InjectMocks
	private WishListController wishListController;

	@Mock
	private WishListService wishListService;

	@Mock
	private Model model;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetWishLists() {
		when(wishListService.getWishLists()).thenReturn(List.of());

		String viewName = wishListController.getWishLists(model);

		verify(wishListService).getWishLists();
		verify(model).addAttribute(eq("wishLists"), any());
		assertEquals("wishlist/list-wishlist", viewName);
	}

	@Test
	void testCreateWishList() {
		CreateWishListRequest request = new CreateWishListRequest(1L);
		when(wishListService.createWishList(request)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

		ResponseEntity<Void> response = wishListController.createWishList(request);

		verify(wishListService).createWishList(request);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void testDeleteWishList() {
		Long wishListId = 1L;

		String redirectUrl = wishListController.deleteWishList(wishListId);

		verify(wishListService).deleteWishList(wishListId);
		assertEquals("redirect:/wish-lists/me", redirectUrl);
	}
}