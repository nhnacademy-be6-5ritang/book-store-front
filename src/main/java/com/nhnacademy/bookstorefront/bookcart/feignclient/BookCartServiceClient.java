package com.nhnacademy.bookstorefront.bookcart.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;

@FeignClient(name = "book-cart-feign-client", url = "http://localhost:8090/api/carts/me")
public interface BookCartServiceClient {

	@GetMapping
	ResponseEntity<List<GetBookCartResponse>> getBookCartsByCartId();

	@PostMapping
	ResponseEntity<Void> createBookCart(@RequestBody CreateBookCartRequest request);

	@PutMapping("/{bookCartId}")
	ResponseEntity<Void> updateBookCart(@PathVariable Long bookCartId, @ModelAttribute UpdateBookCartRequest request);

	@DeleteMapping("/{bookCartId}")
	ResponseEntity<Void> deleteBookCart(@PathVariable Long bookCartId);
}
