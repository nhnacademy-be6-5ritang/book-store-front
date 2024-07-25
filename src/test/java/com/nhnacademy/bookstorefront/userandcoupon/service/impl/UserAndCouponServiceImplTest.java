package com.nhnacademy.bookstorefront.userandcoupon.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookByOrderCouponResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.NoCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.OneCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponOrderResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.feignclient.UserAndCouponFeignClient;

 class UserAndCouponServiceImplTest {

	@Mock
	private UserAndCouponFeignClient userAndCouponFeignClient;

	@Mock
	private OrderServiceClient orderServiceClient;

	@InjectMocks
	private UserAndCouponServiceImpl userAndCouponServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateUserAndCoupon() {
		// Given
		Long couponTemplateId = 1L;

		// When
		userAndCouponServiceImpl.createUserAndCoupon(couponTemplateId);

		// Then
		verify(userAndCouponFeignClient, times(1)).createUserAndCoupon(couponTemplateId);
	}

	@Test
	void testCreateWelcomeCoupon() {
		// Given
		Long userId = 1L;

		// When
		userAndCouponServiceImpl.createWelcomeCoupon(userId);

		// Then
		verify(userAndCouponFeignClient, times(1)).createUserWelcomeCouponIssue(userId);
	}

	@Test
	void testGetAllUserAndCouponPaging() {
		// Given
		Long userId = 1L;
		String type = "welcome";
		Pageable pageable = Pageable.unpaged();
		UserAndCouponResponseDTO responseDTO = new UserAndCouponResponseDTO(
			1L, userId, LocalDateTime.now(), true, LocalDateTime.now(),
			LocalDateTime.now().minusDays(10), BigDecimal.valueOf(100), BigDecimal.valueOf(10), null, null, type, true, null, null, null, null
		);
		Page<UserAndCouponResponseDTO> responsePage = new PageImpl<>(Collections.singletonList(responseDTO));
		when(userAndCouponFeignClient.getAllUsersAndCouponsByManagerPaging(userId, type, pageable))
			.thenReturn(ResponseEntity.ok(responsePage));

		// When
		Page<UserAndCouponResponseDTO> result = userAndCouponServiceImpl.getAllUserAndCouponPaging(userId, type, pageable);

		// Then
		verify(userAndCouponFeignClient, times(1)).getAllUsersAndCouponsByManagerPaging(userId, type, pageable);
		assertEquals(1, result.getTotalElements());
		assertEquals(responseDTO, result.getContent().getFirst());
	}

	@Test
	void testGetUserAndCouponByIdPaging() {
		// Given
		Long userId = 1L;
		String type = "welcome";
		Pageable pageable = Pageable.unpaged();
		UserAndCouponResponseDTO responseDTO = new UserAndCouponResponseDTO(
			1L, userId, LocalDateTime.now(), true, LocalDateTime.now(),
			LocalDateTime.now().minusDays(10), BigDecimal.valueOf(100), BigDecimal.valueOf(10), null, null, type, true, null, null, null, null
		);
		Page<UserAndCouponResponseDTO> responsePage = new PageImpl<>(Collections.singletonList(responseDTO));
		when(userAndCouponFeignClient.getAllUserAndCouponsByUserPaging(pageable))
			.thenReturn(ResponseEntity.ok(responsePage));

		// When
		Page<UserAndCouponResponseDTO> result = userAndCouponServiceImpl.getUserAndCouponByIdPaging(pageable);

		// Then
		verify(userAndCouponFeignClient, times(1)).getAllUserAndCouponsByUserPaging(pageable);
		assertEquals(1, result.getTotalElements());
		assertEquals(responseDTO, result.getContent().getFirst());
	}

	@Test
	void testGetAllUserAndCouponByOrder() {
		// Given
		Long orderListId = 1L;
		Long userId = 1L;
		GetBookByOrderCouponResponse bookResponse = new GetBookByOrderCouponResponse(
			1L, BigDecimal.valueOf(100), List.of(1L)
		);
		UserAndCouponResponseDTO responseDTO = new UserAndCouponResponseDTO(
			1L, userId, LocalDateTime.now(), true, LocalDateTime.now(),
			LocalDateTime.now().minusDays(10), BigDecimal.valueOf(100), BigDecimal.valueOf(10), null, null, "book", true, 1L, "title", null, null
		);
		when(orderServiceClient.getBookByOneOrder(orderListId))
			.thenReturn(ResponseEntity.ok(bookResponse));
		when(userAndCouponFeignClient.findCouponByOrder(
			anyList(), anyList(), any(BigDecimal.class))
		).thenReturn(ResponseEntity.ok(Collections.singletonList(responseDTO)));

		// When
		List<UserAndCouponResponseDTO> result = userAndCouponServiceImpl.getAllUserAndCouponByOrder(orderListId);

		// Then
		verify(orderServiceClient, times(1)).getBookByOneOrder(orderListId);
		verify(userAndCouponFeignClient, times(1)).findCouponByOrder(
			List.of(1L), List.of(1L), BigDecimal.valueOf(100)
		);
		assertEquals(1, result.size());
		assertEquals(responseDTO, result.getFirst());
	}


	 @Test
	 void testGetAllUserAndCouponByOrderWhenBookIdIsNull() {
		 // Given
		 Long orderListId = 1L;
		 GetBookByOrderCouponResponse bookResponse = new GetBookByOrderCouponResponse(
			 null, BigDecimal.valueOf(100), List.of(1L)
		 );

		 when(orderServiceClient.getBookByOneOrder(orderListId))
			 .thenReturn(ResponseEntity.ok(bookResponse));

		 // When & Then
		 assertThrows(IllegalArgumentException.class, () -> {
			 userAndCouponServiceImpl.getAllUserAndCouponByOrder(orderListId);
		 }, "bookId is null");
	 }


	 @Test
	 void testGetAllUserAndCouponByOrderWhenCategoryIdIsNull() {
		 // Given
		 Long orderListId = 1L;
		 GetBookByOrderCouponResponse bookResponse = new GetBookByOrderCouponResponse(
			 1L, BigDecimal.valueOf(100), null
		 );

		 when(orderServiceClient.getBookByOneOrder(orderListId))
			 .thenReturn(ResponseEntity.ok(bookResponse));

		 // When & Then
		 assertThrows(IllegalArgumentException.class, () -> {
			 userAndCouponServiceImpl.getAllUserAndCouponByOrder(orderListId);
		 }, "categoryId is null");
	 }


	 @Test
	 void testGetAllUserAndCouponByOrderWhenBookPriceIsNull() {
		 // Given
		 Long orderListId = 1L;
		 GetBookByOrderCouponResponse bookResponse = new GetBookByOrderCouponResponse(
			 1L, null, List.of(1L)
		 );

		 when(orderServiceClient.getBookByOneOrder(orderListId))
			 .thenReturn(ResponseEntity.ok(bookResponse));

		 // When & Then
		 assertThrows(IllegalArgumentException.class, () -> {
			 userAndCouponServiceImpl.getAllUserAndCouponByOrder(orderListId);
		 }, "bookPrice is null");
	 }


	 @Test
	 void testGetAllUserAndCouponByOrderWithNullResponse() {
		 Long orderListId = 1L;
		 when(orderServiceClient.getBookByOneOrder(orderListId)).thenReturn(ResponseEntity.ok(null));

		 Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			 userAndCouponServiceImpl.getAllUserAndCouponByOrder(orderListId);
		 });

		 assertEquals("bookId is null", exception.getMessage());
	 }



	 @Test
	void testGetAllUserAndCouponByCartOrder() {
		// Given
		List<GetBookByOrderCouponResponse> bookDetails = Collections.singletonList(
			new GetBookByOrderCouponResponse(1L, BigDecimal.valueOf(100), List.of(1L))
		);
		Long userId = 1L;
		UserAndCouponResponseDTO responseDTO = new UserAndCouponResponseDTO(
			1L, userId, LocalDateTime.now(), true, LocalDateTime.now(),
			LocalDateTime.now().minusDays(10), BigDecimal.valueOf(100), BigDecimal.valueOf(10), null, null, "book", true, 1L, "title", null, null
		);
		when(userAndCouponFeignClient.findCouponByCartOrder(bookDetails))
			.thenReturn(ResponseEntity.ok(Collections.singletonList(responseDTO)));

		// When
		List<UserAndCouponResponseDTO> result = userAndCouponServiceImpl.getAllUserAndCouponByCartOrder(bookDetails);

		// Then
		verify(userAndCouponFeignClient, times(1)).findCouponByCartOrder(bookDetails);
		assertEquals(1, result.size());
		assertEquals(responseDTO, result.get(0));
	}

	@Test
	void testGetCartOrderCouponByBookDetails() {
		// Given
		Long orderListId = 1L;
		GetBookByOrderCouponResponse bookResponse = new GetBookByOrderCouponResponse(
			1L, BigDecimal.valueOf(100), List.of(1L)
		);
		when(orderServiceClient.getBookByOneOrder(orderListId))
			.thenReturn(ResponseEntity.ok(bookResponse));

		// When
		GetBookByOrderCouponResponse result = userAndCouponServiceImpl.getCartOrderCouponByBookDetails(orderListId);

		// Then
		verify(orderServiceClient, times(1)).getBookByOneOrder(orderListId);
		assertEquals(bookResponse, result);
	}

	@Test
	void testGetSelectedCouponByOrder() {
		// Given
		Long couponId = 1L;
		UserAndCouponOrderResponseDTO responseDTO = new UserAndCouponOrderResponseDTO(
			1L, BigDecimal.valueOf(100), BigDecimal.valueOf(10), null, null, "welcome"
		);
		when(userAndCouponFeignClient.getSelectedCoupon(couponId))
			.thenReturn(ResponseEntity.ok(responseDTO));

		// When
		UserAndCouponOrderResponseDTO result = userAndCouponServiceImpl.getSelectedCouponByOrder(couponId);

		// Then
		verify(userAndCouponFeignClient, times(1)).getSelectedCoupon(couponId);
		assertEquals(responseDTO, result);
	}

	@Test
	void testOneCouponReturnModel() {
		// Given
		UserAndCouponOrderResponseDTO couponResponseDTO = new UserAndCouponOrderResponseDTO(
			1L, BigDecimal.valueOf(100), BigDecimal.valueOf(10),null,
			null, "WELCOME"
		);




		GetBookOrderResponse bookOrderResponse = new GetBookOrderResponse(
			new GetBookOrderGetBookResponse("title", BigDecimal.valueOf(200), "description", 1L), 2, 1L, 1L
		);
		GetDeliveryPolicyResponse deliveryPolicyResponse = new GetDeliveryPolicyResponse(1L, "name", BigDecimal.valueOf(100),
			"content",BigDecimal.valueOf(30)
		);
		GetListWrappingResponse wrappingResponse = new GetListWrappingResponse(
			List.of(new GetWrappingResponse(1L, "name", BigDecimal.valueOf(100), 1))
		);

		// When
		OneCouponResponseDTO result = userAndCouponServiceImpl.oneCouponReturnModel(
			couponResponseDTO, bookOrderResponse, deliveryPolicyResponse, wrappingResponse
		);

		// Then
		BigDecimal orderPrice = BigDecimal.valueOf(200).multiply(BigDecimal.valueOf(2));
		BigDecimal wrappingTotalPrice = BigDecimal.valueOf(100);
		BigDecimal deliveryPrice = BigDecimal.valueOf(100);

		BigDecimal expectedOrderPriceAfterCoupon = orderPrice.subtract(BigDecimal.valueOf(10));
		BigDecimal expectedOrderPriceBeforePoint = expectedOrderPriceAfterCoupon.add(wrappingTotalPrice).add(deliveryPrice);

		OneCouponResponseDTO expectedResponse = new OneCouponResponseDTO(
			orderPrice, BigDecimal.valueOf(10), expectedOrderPriceAfterCoupon, expectedOrderPriceBeforePoint
		);

		assertEquals(expectedResponse, result);
	}



	 @Test
	 void testOneCouponReturnModelWithPercentageDiscount() {
		 // Given
		 UserAndCouponOrderResponseDTO couponResponseDTO = new UserAndCouponOrderResponseDTO(
			 1L, BigDecimal.valueOf(50), null, BigDecimal.valueOf(0.2), BigDecimal.valueOf(30), "welcome"
		 );

		 GetBookOrderResponse bookOrderResponse = new GetBookOrderResponse(
			 new GetBookOrderGetBookResponse("title", BigDecimal.valueOf(200), "description", 1L), 2, 1L, 1L
		 );
		 GetDeliveryPolicyResponse deliveryPolicyResponse = new GetDeliveryPolicyResponse(
			 1L, "name", BigDecimal.valueOf(100), "content", BigDecimal.valueOf(30)
		 );
		 GetListWrappingResponse wrappingResponse = new GetListWrappingResponse(
			 List.of(new GetWrappingResponse(1L, "name", BigDecimal.valueOf(50), 1))
		 );

		 // When
		 OneCouponResponseDTO result = userAndCouponServiceImpl.oneCouponReturnModel(
			 couponResponseDTO, bookOrderResponse, deliveryPolicyResponse, wrappingResponse
		 );

		 // Then
		 BigDecimal orderPrice = BigDecimal.valueOf(200).multiply(BigDecimal.valueOf(2)); // 200 * 2 = 400
		 BigDecimal wrappingTotalPrice = BigDecimal.valueOf(50); // Wrapping price
		 BigDecimal deliveryPrice = BigDecimal.valueOf(100);

		 // Calculate the discount based on saleRate and maxSalePrice
		 BigDecimal calculatedDiscount = orderPrice.multiply(BigDecimal.valueOf(0.2)).setScale(0, RoundingMode.CEILING); // 20% of 400 = 80
		 BigDecimal maxSalePrice = BigDecimal.valueOf(30);
		 if (calculatedDiscount.compareTo(maxSalePrice) > 0) {
			 calculatedDiscount = maxSalePrice; // Discount should be capped at 30
		 }

		 BigDecimal expectedDiscount = calculatedDiscount; // 30
		 BigDecimal expectedOrderPriceAfterCoupon = orderPrice.subtract(expectedDiscount); // 400 - 30 = 370
		 BigDecimal expectedOrderPriceBeforePoint = expectedOrderPriceAfterCoupon.add(wrappingTotalPrice).add(deliveryPrice); // 370 + 50 + 100 = 520

		 OneCouponResponseDTO expectedResponse = new OneCouponResponseDTO(
			 orderPrice, expectedDiscount, expectedOrderPriceAfterCoupon, expectedOrderPriceBeforePoint
		 );

		 assertEquals(expectedResponse, result);
	 }

	 @Test
	 void testOneCouponReturnModelCartWithFlatDiscount() {
		 // Given
		 UserAndCouponOrderResponseDTO userAndCouponOrderResponseDTO = new UserAndCouponOrderResponseDTO(
			 1L, BigDecimal.valueOf(100), BigDecimal.valueOf(10), null, null, "welcome"
		 );
		 BigDecimal orderPrice = BigDecimal.valueOf(100);
		 BigDecimal deliveryPrice = BigDecimal.valueOf(10);
		 BigDecimal wrappingTotalPrice = BigDecimal.valueOf(5);

		 // When
		 OneCouponResponseDTO result = userAndCouponServiceImpl.oneCouponReturnModelCart(
			 userAndCouponOrderResponseDTO, orderPrice, deliveryPrice, wrappingTotalPrice
		 );

		 // Then
		 assertEquals(BigDecimal.valueOf(100), result.orderPrice());
		 assertEquals(BigDecimal.valueOf(10), result.discount());
		 assertEquals(BigDecimal.valueOf(90), result.orderPriceAfterCoupon());
		 assertEquals(BigDecimal.valueOf(105), result.orderPriceBeforePoint());
	 }

	 @Test
	 void testOneCouponReturnModelCartWithPercentageDiscountWithinLimit() {
		 // Given
		 UserAndCouponOrderResponseDTO userAndCouponOrderResponseDTO = new UserAndCouponOrderResponseDTO(
			 1L, BigDecimal.valueOf(100), null, BigDecimal.valueOf(0.2), BigDecimal.valueOf(100), "welcome"
		 );
		 BigDecimal orderPrice = BigDecimal.valueOf(200);
		 BigDecimal deliveryPrice = BigDecimal.valueOf(10);
		 BigDecimal wrappingTotalPrice = BigDecimal.valueOf(5);

		 // When
		 OneCouponResponseDTO result = userAndCouponServiceImpl.oneCouponReturnModelCart(
			 userAndCouponOrderResponseDTO, orderPrice, deliveryPrice, wrappingTotalPrice
		 );

		 // Then
		 BigDecimal calculatedDiscount = orderPrice.multiply(BigDecimal.valueOf(0.2)).setScale(0, RoundingMode.CEILING);
		 assertEquals(calculatedDiscount, result.discount());
		 assertEquals(orderPrice.subtract(calculatedDiscount), result.orderPriceAfterCoupon());
		 assertEquals(orderPrice.subtract(calculatedDiscount).add(deliveryPrice).add(wrappingTotalPrice), result.orderPriceBeforePoint());
	 }

	 @Test
	 void testOneCouponReturnModelCartWithPercentageDiscountExceedingLimit() {
		 // Given
		 UserAndCouponOrderResponseDTO userAndCouponOrderResponseDTO = new UserAndCouponOrderResponseDTO(
			 1L, BigDecimal.valueOf(20), null, BigDecimal.valueOf(0.2), BigDecimal.valueOf(10), "welcome"
		 );
		 BigDecimal orderPrice = BigDecimal.valueOf(100);
		 BigDecimal deliveryPrice = BigDecimal.valueOf(10);
		 BigDecimal wrappingTotalPrice = BigDecimal.valueOf(5);

		 // When
		 OneCouponResponseDTO result = userAndCouponServiceImpl.oneCouponReturnModelCart(
			 userAndCouponOrderResponseDTO, orderPrice, deliveryPrice, wrappingTotalPrice
		 );

		 // Then
		 assertEquals(BigDecimal.valueOf(10), result.discount());
		 assertEquals(orderPrice.subtract(BigDecimal.valueOf(10)), result.orderPriceAfterCoupon());
		 assertEquals(orderPrice.subtract(BigDecimal.valueOf(10)).add(deliveryPrice).add(wrappingTotalPrice), result.orderPriceBeforePoint());
	 }

	 @Test
	 void testOneCouponReturnModelCartWithNoDiscount() {
		 // Given
		 UserAndCouponOrderResponseDTO userAndCouponOrderResponseDTO = new UserAndCouponOrderResponseDTO(
			 null, null, null, null, null, "welcome"
		 );
		 BigDecimal orderPrice = BigDecimal.valueOf(100);
		 BigDecimal deliveryPrice = BigDecimal.valueOf(10);
		 BigDecimal wrappingTotalPrice = BigDecimal.valueOf(5);

		 // When
		 OneCouponResponseDTO result = userAndCouponServiceImpl.oneCouponReturnModelCart(
			 userAndCouponOrderResponseDTO, orderPrice, deliveryPrice, wrappingTotalPrice
		 );

		 // Then
		 assertEquals(BigDecimal.ZERO, result.discount());
		 assertEquals(orderPrice, result.orderPriceAfterCoupon());
		 assertEquals(orderPrice.add(deliveryPrice).add(wrappingTotalPrice), result.orderPriceBeforePoint());
	 }




	@Test
	void testNoCouponReturnModel() {
		// Given
		GetBookOrderResponse bookOrderResponse = new GetBookOrderResponse(
			new GetBookOrderGetBookResponse("title", BigDecimal.valueOf(200), "description", 1L), 2, 1L, 1L
		);
		GetDeliveryPolicyResponse deliveryPolicyResponse = new GetDeliveryPolicyResponse(1L, "name", BigDecimal.valueOf(100),
			"content",BigDecimal.valueOf(30)
		);
		GetListWrappingResponse wrappingResponse = new GetListWrappingResponse(
			List.of(new GetWrappingResponse(1L, "name", BigDecimal.valueOf(100), 1))
		);


		// When
		NoCouponResponseDTO result = userAndCouponServiceImpl.noCouponReturnModel(
			bookOrderResponse, deliveryPolicyResponse, wrappingResponse
		);

		// Then
		BigDecimal orderPrice = BigDecimal.valueOf(200).multiply(BigDecimal.valueOf(2));
		BigDecimal wrappingTotalPrice = BigDecimal.valueOf(100);
		BigDecimal deliveryPrice = BigDecimal.valueOf(100);
		BigDecimal expectedOrderPriceBeforePoint = orderPrice.add(wrappingTotalPrice).add(deliveryPrice);

		NoCouponResponseDTO expectedResponse = new NoCouponResponseDTO(
			orderPrice, BigDecimal.ZERO, expectedOrderPriceBeforePoint
		);

		assertEquals(expectedResponse, result);
	}



	 @Test
	 void testNoCouponReturnModelCart() {
		 // Given
		 BigDecimal orderPrice = BigDecimal.valueOf(100);
		 BigDecimal deliveryPrice = BigDecimal.valueOf(10);
		 BigDecimal wrappingTotalPrice = BigDecimal.valueOf(5);

		 // Expected values
		 BigDecimal expectedDiscount = BigDecimal.ZERO;
		 BigDecimal expectedOrderPriceBeforePoint = orderPrice.add(deliveryPrice).add(wrappingTotalPrice);

		 // When
		 NoCouponResponseDTO result = userAndCouponServiceImpl.noCouponReturnModelCart(
			 orderPrice, deliveryPrice, wrappingTotalPrice
		 );

		 // Then
		 assertEquals(orderPrice, result.orderPrice());
		 assertEquals(expectedDiscount, result.discount());
		 assertEquals(expectedOrderPriceBeforePoint, result.orderPriceBeforePoint());
	 }

	@Test
	void testIsRealUserCheck() {
		// Given
		Boolean isRealUser = true;
		when(userAndCouponFeignClient.isRealUserCheck()).thenReturn(ResponseEntity.ok(isRealUser));

		// When
		Boolean result = userAndCouponServiceImpl.isRealUserCheck();

		// Then
		verify(userAndCouponFeignClient, times(1)).isRealUserCheck();
		assertEquals(isRealUser, result);
	}

	@Test
	void testUpdateCouponAfterPayment() {
		// Given
		Long couponId = 1L;

		// When
		userAndCouponServiceImpl.updateCouponAfterPayment(couponId);

		// Then
		verify(userAndCouponFeignClient, times(1)).updateCouponAfterPayment(couponId);
	}
}
