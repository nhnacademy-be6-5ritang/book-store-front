// package com.nhnacademy.bookstorefront.couponpolicy.controller;
//
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import java.math.BigDecimal;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.MockitoAnnotations;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.nhnacademy.bookstorefront.couponpolicy.domain.dto.request.CouponPolicyCreateRequestDTO;
// import com.nhnacademy.bookstorefront.couponpolicy.service.impl.CouponPolicyServiceImpl;
// import com.nhnacademy.bookstorefront.global.config.CacheConfig;
// import com.nhnacademy.bookstorefront.global.controller.GlobalDataControllerAdvice;
//
// @WebMvcTest(CouponPolicyController.class)
// class CouponPolicyControllerTest {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@Autowired
// 	private ObjectMapper objectMapper;
//
// 	@MockBean
// 	private CouponPolicyServiceImpl couponPolicyService;
//
//
// 	@MockBean
// 	private GlobalDataControllerAdvice globalDataControllerAdvice;
//
// 	@MockBean
// 	private CacheConfig cacheConfig;
//
// 	@BeforeEach
// 	void setUp() {
// 		MockitoAnnotations.openMocks(this);
// 		mockMvc = MockMvcBuilders.standaloneSetup(new CouponPolicyController(couponPolicyService)).build();
// 	}
//
//
// 	@Test
// 	void testCreateCouponPolicy_Success() throws Exception {
// 		// given
// 		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
// 			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "welcome", null, null, null, null
// 		);
//
// 		doNothing().when(couponPolicyService).issueWelcomeCoupon(requestDTO);
//
// 		// when
// 		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
// 				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
// 				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
// 				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
// 				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
// 				.param("type", requestDTO.type())
// 				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
// 				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
// 				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
// 				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
// 			.andExpect(status().is3xxRedirection())
// 			.andExpect(redirectedUrl("/coupons/policies"))
// 			.andDo(print());
//
// 		// then
// 		verify(couponPolicyService, times(1)).issueWelcomeCoupon(any(CouponPolicyCreateRequestDTO.class));
// 	}
//
//
// 	@Test
// 	void testCreateCouponPolicy_Success_Birthday() throws Exception {
// 		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
// 			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "birthday", null, null, null, null
// 		);
//
// 		doNothing().when(couponPolicyService).issueBirthdayCoupon(requestDTO);
//
// 		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
// 				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
// 				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
// 				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
// 				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
// 				.param("type", requestDTO.type())
// 				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
// 				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
// 				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
// 				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
// 			.andExpect(status().is3xxRedirection())
// 			.andExpect(redirectedUrl("/coupons/policies"))
// 			.andDo(MockMvcResultHandlers.print());
//
// 		verify(couponPolicyService, times(1)).issueBirthdayCoupon(any(CouponPolicyCreateRequestDTO.class));
// 	}
//
//
//
// 	@Test
// 	void testCreateCouponPolicy_Success_Book() throws Exception {
// 		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
// 			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "book", 1L, "Some Book", null, null
// 		);
//
// 		doNothing().when(couponPolicyService).issueBookCoupon(requestDTO);
//
// 		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
// 				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
// 				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
// 				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
// 				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
// 				.param("type", requestDTO.type())
// 				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
// 				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
// 				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
// 				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
// 			.andExpect(status().is3xxRedirection())
// 			.andExpect(redirectedUrl("/coupons/policies"))
// 			.andDo(MockMvcResultHandlers.print());
//
// 		verify(couponPolicyService, times(1)).issueBookCoupon(any(CouponPolicyCreateRequestDTO.class));
// 	}
//
// 	@Test
// 	void testCreateCouponPolicy_Success_Category() throws Exception {
// 		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
// 			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "category", null, null, 1L, "Some Category"
// 		);
//
// 		doNothing().when(couponPolicyService).issueCategoryCoupon(requestDTO);
//
// 		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
// 				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
// 				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
// 				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
// 				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
// 				.param("type", requestDTO.type())
// 				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
// 				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
// 				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
// 				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
// 			.andExpect(status().is3xxRedirection())
// 			.andExpect(redirectedUrl("/coupons/policies"))
// 			.andDo(MockMvcResultHandlers.print());
//
// 		verify(couponPolicyService, times(1)).issueCategoryCoupon(any(CouponPolicyCreateRequestDTO.class));
// 	}
//
// 	@Test
// 	void testCreateCouponPolicy_Success_Sale() throws Exception {
// 		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
// 			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "sale", null, null, null, null
// 		);
//
// 		doNothing().when(couponPolicyService).issueSaleCoupon(requestDTO);
//
// 		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
// 				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
// 				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
// 				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
// 				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
// 				.param("type", requestDTO.type())
// 				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
// 				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
// 				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
// 				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
// 			.andExpect(status().is3xxRedirection())
// 			.andExpect(redirectedUrl("/coupons/policies"))
// 			.andDo(MockMvcResultHandlers.print());
//
// 		verify(couponPolicyService, times(1)).issueSaleCoupon(any(CouponPolicyCreateRequestDTO.class));
// 	}
//
// 	@Test
// 	void testCreateCouponPolicy_Failure_BookIdNull() throws Exception {
// 		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
// 			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "book", null, "Some Book", null, null
// 		);
//
// 		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
// 				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
// 				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
// 				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
// 				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
// 				.param("type", requestDTO.type())
// 				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
// 				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
// 				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
// 				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
// 			.andExpect(status().isBadRequest())
// 			.andExpect(MockMvcResultMatchers.content().string("책쿠폰 정책등록시 book id가 필요합니다."))
// 			.andDo(MockMvcResultHandlers.print());
//
// 		verify(couponPolicyService, times(0)).issueSaleCoupon(any(CouponPolicyCreateRequestDTO.class));
// 	}
//
// 	@Test
// 	void testCreateCouponPolicy_Failure_CategoryIdNull() throws Exception {
// 		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
// 			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "category", null, null, 1L, null
// 		);
//
// 		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
// 				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
// 				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
// 				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
// 				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
// 				.param("type", requestDTO.type())
// 				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
// 				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
// 				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
// 				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
// 			.andExpect(status().isBadRequest())
// 			.andExpect(MockMvcResultMatchers.content().string("카테고리쿠폰 정책등록시 category id가 필요합니다."))
// 			.andDo(MockMvcResultHandlers.print());
//
//
// 		verify(couponPolicyService, times(0)).issueCategoryCoupon(any(CouponPolicyCreateRequestDTO.class));
// 	}
//
// 	@Test
// 	void testCreateCouponPolicy_Failure_InvalidType() throws Exception {
// 		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
// 			BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), null, null, "invalid", null, null, null, null
// 		);
//
// 		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
// 				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
// 				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
// 				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
// 				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
// 				.param("type", requestDTO.type())
// 				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
// 				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
// 				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
// 				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
// 			.andExpect(status().isBadRequest())
// 			.andExpect(MockMvcResultMatchers.content().string("해당 쿠폰 타입은 등록할 수 없습니다."))
// 			.andDo(MockMvcResultHandlers.print());
// 	}
//
// 	@Test
// 	void testCreateCouponPolicy_Failure_Validation() throws Exception {
// 		CouponPolicyCreateRequestDTO requestDTO = new CouponPolicyCreateRequestDTO(
// 			BigDecimal.valueOf(10000), null, null, BigDecimal.valueOf(5000), "welcome", null, null, null, null
// 		);
//
// 		mockMvc.perform(MockMvcRequestBuilders.post("/coupons/policies")
// 				.param("minOrderPrice", requestDTO.minOrderPrice().toString())
// 				.param("salePrice", requestDTO.salePrice() != null ? requestDTO.salePrice().toString() : "")
// 				.param("saleRate", requestDTO.saleRate() != null ? requestDTO.saleRate().toString() : "")
// 				.param("maxSalePrice", requestDTO.maxSalePrice() != null ? requestDTO.maxSalePrice().toString() : "")
// 				.param("type", requestDTO.type())
// 				.param("bookId", requestDTO.bookId() != null ? requestDTO.bookId().toString() : "")
// 				.param("bookTitle", requestDTO.bookTitle() != null ? requestDTO.bookTitle() : "")
// 				.param("categoryId", requestDTO.categoryId() != null ? requestDTO.categoryId().toString() : "")
// 				.param("categoryName", requestDTO.categoryName() != null ? requestDTO.categoryName() : ""))
// 			.andExpect(status().isBadRequest())
// 			.andExpect(MockMvcResultMatchers.content().string("쿠폰 정책등록시 할인가격은 할인률, 최대할인가격과 함께 등록할 수 없습니다."))
// 			.andDo(MockMvcResultHandlers.print());
// 	}
// }
