package com.nhnacademy.bookstorefront.bookcart.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.bookstorefront.bookcart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstorefront.bookcart.dto.response.CreateBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.dto.response.UpdateBookCartResponse;

@FeignClient(name = "book-cart-feign-client", url = "http://localhost:8090/api/carts")
public interface BookCartServiceClient {

	@PostMapping
	ResponseEntity<CreateBookCartResponse> createBookCart(@RequestBody CreateBookCartRequest request);

	@GetMapping
	ResponseEntity<List<GetBookCartResponse>> getBookCartsByUserId();

	@PutMapping("/{bookCartId}")
	ResponseEntity<UpdateBookCartResponse> updateBookCart(@PathVariable Long bookCartId,
		@RequestBody UpdateBookCartRequest request);

	@DeleteMapping("/{bookCartId}")
	ResponseEntity<Void> deleteBookCart(@PathVariable Long bookCartId);
}
