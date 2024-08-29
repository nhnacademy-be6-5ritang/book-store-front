package com.nhnacademy.bookstorefront.product.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.bookstorefront.product.dto.response.GetProductResponse;
import com.nhnacademy.bookstorefront.product.dto.response.GetProductSimpleResponse;

@FeignClient(name = "product-feign-client", url = "http://localhost:8090/api/products")
public interface ProductServiceClient {

	@GetMapping("/{bookId}")
	ResponseEntity<GetProductResponse> getProduct(@PathVariable Long bookId);

	@GetMapping("/best-seller")
	ResponseEntity<List<GetProductSimpleResponse>> getBestSellerBooks();

	@GetMapping("/newest")
	ResponseEntity<List<GetProductSimpleResponse>> getNewestBooks();

	@GetMapping("/likes")
	ResponseEntity<List<GetProductSimpleResponse>> getLikesBooks();

	@GetMapping("/category")
	ResponseEntity<Page<GetProductSimpleResponse>> getBooksByCategoryName(
		@PageableDefault(page = 1, size = 12) Pageable pageable, @RequestParam String categoryName);
}
