package com.nhnacademy.bookstorefront.product.service.impl;

import com.nhnacademy.bookstorefront.product.dto.response.GetProductResponse;
import com.nhnacademy.bookstorefront.product.dto.response.GetProductSimpleResponse;
import com.nhnacademy.bookstorefront.product.feignclient.ProductServiceClient;
import com.nhnacademy.bookstorefront.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductServiceClient productServiceClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public GetProductResponse getProduct(Long bookId) {
        return productServiceClient.getProduct(bookId).getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GetProductSimpleResponse> getNewestBooks() {
        return productServiceClient.getNewestBooks().getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GetProductSimpleResponse> getBestSellerBooks() {
        return productServiceClient.getBestSellerBooks().getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GetProductSimpleResponse> getLikesBooks() {
        return productServiceClient.getLikesBooks().getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GetProductSimpleResponse> getBooksByCategory(Pageable pageable, String categoryName) {
        return productServiceClient.getBooksByCategoryName(pageable, categoryName).getBody();
    }

}
