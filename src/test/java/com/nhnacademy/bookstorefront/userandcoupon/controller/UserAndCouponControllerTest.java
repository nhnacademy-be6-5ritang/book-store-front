package com.nhnacademy.bookstorefront.userandcoupon.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nhnacademy.bookstorefront.global.config.CacheConfig;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookByOrderCouponResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.service.Impl.BookOrderServiceImpl;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

@WebMvcTest(UserAndCouponController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserAndCouponControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserAndCouponService userAndCouponService;

	@MockBean
	private BookOrderServiceImpl bookOrderServiceImpl;

	@MockBean
	private CacheConfig cacheConfig;

	@InjectMocks
	private UserAndCouponController userAndCouponController;



	@Test
	void testCreateUserAndCoupon() throws Exception {
		// Given
		Long couponId = 1L;

		// When
		mockMvc.perform(post("/coupons/{couponId}", couponId))
			.andExpect(status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/coupons/issue?message=" + URLEncoder.encode("쿠폰 발급 완료", StandardCharsets.UTF_8)));

		// Then
		verify(userAndCouponService, times(1)).createUserAndCoupon(anyLong());
	}


	@Test
	void testCreateUserAndCoupon_Exception() throws Exception {
		// Given
		Long couponId = 1L;
		doThrow(new RuntimeException("Error creating coupon")).when(userAndCouponService).createUserAndCoupon(anyLong());

		// When
		mockMvc.perform(post("/coupons/{couponId}", couponId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/auth/login"));

		// Then
		verify(userAndCouponService, times(1)).createUserAndCoupon(couponId);
	}

	@Test
	void testGetOrderCoupon() throws Exception {
		// Given
		Long orderListId = 1L;
		Long deliveryId = 1L;
		List<UserAndCouponResponseDTO> couponList = new ArrayList<>();
		when(userAndCouponService.isRealUserCheck()).thenReturn(true);
		when(userAndCouponService.getAllUserAndCouponByOrder(anyLong())).thenReturn(couponList);

		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/coupons/orders/{orderListId}/users/{deliveryId}", orderListId, deliveryId))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("coupon-user/order-use-coupon"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("orderListId"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("deliveryId"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("couponList"));

		// Then
		verify(userAndCouponService, times(1)).getAllUserAndCouponByOrder(anyLong());
	}

	@Test
	void testSelectCouponByOrder() throws Exception {
		// Given
		Long orderListId = 1L;
		Long deliveryId = 1L;
		Long couponId = 2L;

		// When
		mockMvc.perform(post("/coupons/orders/{orderListId}/users/{deliveryId}", orderListId, deliveryId)
				.param("couponId", couponId.toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/api/orders/createOrderTest/" + orderListId + "/" + deliveryId + "?couponId=" + couponId));


	}



	@Test
	void testGetCartOrderCoupon() throws Exception {
		// Given
		Long deliveryId = 1L;
		String orderInfoId = "order1";

		// Create mock book orders
		List<GetBookOrderResponse> bookOrders = List.of(
			new GetBookOrderResponse(
				new GetBookOrderGetBookResponse("dd", BigDecimal.valueOf(10), "ㅇㅇ", 1L), // Mock inner response
				2,
				1L,
				100L
			)
		);

		// Create mock book details
		List<GetBookByOrderCouponResponse> bookDetails = List.of(
			new GetBookByOrderCouponResponse(1L, BigDecimal.valueOf(10), List.of(1L, 2L))
		);

		// Create mock coupon list
		List<UserAndCouponResponseDTO> couponList = new ArrayList<>();

		when(userAndCouponService.isRealUserCheck()).thenReturn(true);
		when(bookOrderServiceImpl.getBookOrderByOrderId(anyString())).thenReturn(bookOrders);
		when(userAndCouponService.getCartOrderCouponByBookDetails(anyLong())).thenReturn(bookDetails.getFirst());
		when(userAndCouponService.getAllUserAndCouponByCartOrder(anyList())).thenReturn(couponList);

		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/coupons/orders/users/{deliveryId}/{orderInfoId}", deliveryId, orderInfoId))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("coupon-user/cart-order-use-coupon"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("deliveryId"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("couponList"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("orderInfoId"));

		// Then
		verify(bookOrderServiceImpl, times(1)).getBookOrderByOrderId(anyString());
		verify(userAndCouponService, times(1)).getAllUserAndCouponByCartOrder(anyList());
	}


	@Test
	void testSelectCouponByOrderCart() throws Exception {
		// Given
		Long deliveryId = 1L;
		String orderInfoId = "order1";
		Long couponId = 2L;

		// When
		mockMvc.perform(post("/coupons/orders/users/{deliveryId}/cart/{orderInfoId}", deliveryId, orderInfoId)
				.param("couponId", couponId.toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/api/orders/createOrderTest/" + deliveryId + "/cart/" + orderInfoId + "?couponId=" + couponId));

		// Then
		// No direct service method verification needed for this redirection test.
	}

	@Test
	void testGetUserAndCouponByIdPaging() throws Exception {
		// Given
		Page<UserAndCouponResponseDTO> userAndCoupon = new PageImpl<>(new ArrayList<>());
		when(userAndCouponService.getUserAndCouponByIdPaging(any(Pageable.class))).thenReturn(userAndCoupon);

		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/coupons/users/user")
				.param("page", "1")
				.param("size", "4"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("coupon-user/mypage-coupon"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("userAndCoupon"));

		// Then
		verify(userAndCouponService, times(1)).getUserAndCouponByIdPaging(any(Pageable.class));
	}

	@Test
	void testGetAllUserAndCouponPaging() throws Exception {
		// Given
		Long userId = 1L;
		String type = "someType";
		Page<UserAndCouponResponseDTO> userAndCoupon = new PageImpl<>(new ArrayList<>());
		when(userAndCouponService.getAllUserAndCouponPaging(anyLong(), anyString(), any(Pageable.class))).thenReturn(userAndCoupon);

		// When
		mockMvc.perform(MockMvcRequestBuilders.get("/coupons/users")
				.param("userId", userId.toString())
				.param("type", type)
				.param("page", "1")
				.param("size", "4"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("coupon-manager/coupon-issued"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("userAndCoupon"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("startPage"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("endPage"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("param"));

		// Then
		verify(userAndCouponService, times(1)).getAllUserAndCouponPaging(anyLong(), anyString(), any(Pageable.class));
	}
}