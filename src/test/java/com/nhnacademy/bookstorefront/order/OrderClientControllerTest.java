package com.nhnacademy.bookstorefront.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstorefront.bookcart.dto.response.GetBookCartResponse;
import com.nhnacademy.bookstorefront.bookcart.service.BookCartService;
import com.nhnacademy.bookstorefront.delivery.dto.response.GetDeliveryResponse;
import com.nhnacademy.bookstorefront.delivery.service.impl.DeliveryServiceImpl;
import com.nhnacademy.bookstorefront.deliverypolicy.dto.response.GetDeliveryPolicyResponse;
import com.nhnacademy.bookstorefront.deliverypolicy.service.impl.DeliveryPolicyServiceImpl;
import com.nhnacademy.bookstorefront.global.config.CacheConfig;
import com.nhnacademy.bookstorefront.order.controller.OrderClientController;
import com.nhnacademy.bookstorefront.order.dto.request.CreateBookOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateCartOrderPost;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderListPost;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateOrderStatusRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.request.CreateWrappingTypeRequest;
import com.nhnacademy.bookstorefront.order.dto.request.OrderCheckNonRequest;
import com.nhnacademy.bookstorefront.order.dto.request.UpdateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.request.UpdateWrappingTypeRequest;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderGetOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateCartOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.CreateOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAdminAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllListOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllOrderByStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllRefundResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderGetBookResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetBookOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetListWrappingResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetNonOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderByInfoResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetOrderStatusResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetPaperResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetRefundResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetUserPointOrderResponse;
import com.nhnacademy.bookstorefront.order.dto.response.GetWrappingResponse;
import com.nhnacademy.bookstorefront.order.service.Impl.BookOrderServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.OrderServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.OrderStatusServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.PaperTypeServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.RefundPolicyServiceImpl;
import com.nhnacademy.bookstorefront.order.service.Impl.WrappingPaperServiceImpl;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.NoCouponResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.domain.dto.response.UserAndCouponOrderResponseDTO;
import com.nhnacademy.bookstorefront.userandcoupon.service.UserAndCouponService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import java.util.List;

import jakarta.servlet.http.Cookie;

@WebMvcTest(OrderClientController.class)
class OrderClientControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@MockBean
	private BookOrderServiceImpl bookOrderServiceImpl;

	@MockBean
	private OrderServiceImpl orderServiceImpl;

	@MockBean
	private PaperTypeServiceImpl paperTypeServiceImpl;

	@MockBean
	private WrappingPaperServiceImpl wrappingPaperServiceImpl;

	@MockBean
	private DeliveryServiceImpl deliveryServiceImpl;

	@MockBean
	private BookServiceImpl bookServiceImpl;

	@MockBean
	private DeliveryPolicyServiceImpl deliveryPolicyServiceImpl;

	@MockBean
	private RefundPolicyServiceImpl refundPolicyServiceImpl;

	@MockBean
	private UserAndCouponService userAndCouponService;

	@MockBean
	private BookCartService bookCartService;

	@MockBean
	private OrderStatusServiceImpl orderStatusServiceImpl;

	@InjectMocks
	private OrderClientController orderClientController;

	@MockBean
	private CacheConfig cacheConfig;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new OrderClientController(
				bookOrderServiceImpl,
				orderServiceImpl,paperTypeServiceImpl,
				wrappingPaperServiceImpl,
				deliveryServiceImpl,
				bookServiceImpl,deliveryPolicyServiceImpl,
				refundPolicyServiceImpl,userAndCouponService,bookCartService,orderStatusServiceImpl))
			.build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testCreateBookOrder() throws Exception {
		// Create dummy data for CreateBookOrderGetBookResponse
		CreateBookOrderGetBookResponse bookResponse = new CreateBookOrderGetBookResponse(
			"Dummy Book Title",
			BigDecimal.valueOf(19.99),
			"This is a dummy book description."
		);

		// Create dummy data for CreateBookOrderGetOrderResponse
		CreateBookOrderGetOrderResponse orderResponse = new CreateBookOrderGetOrderResponse(
			"Dummy Info ID",
			BigDecimal.valueOf(19.99),
			LocalDateTime.now(),
			BigDecimal.valueOf(2.00),
			BigDecimal.valueOf(1.00)
		);

		// Create dummy data for CreateBookOrderResponse
		CreateBookOrderResponse response = new CreateBookOrderResponse(
			bookResponse,
			orderResponse,
			1, // dummy quantity
			123L // dummy orderListId
		);

		// Mock the service method
		when(bookOrderServiceImpl.createBookOrder(any(CreateBookOrderRequest.class))).thenReturn(response);

		// Perform the request and validate the results
		mockMvc.perform(post("/api/orders/createBookOrderTest")
				.param("bookId", "1")
				.param("quantity", "1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/createOrderTestPaper/123")); // Note the change here
	}

	@Test
	void testCreateOrderTestPaperGet() throws Exception {
		// Create dummy data for GetPaperResponse
		GetPaperResponse paper1 = new GetPaperResponse(1L, "Paper 1", "Content 1", BigDecimal.valueOf(1.99));
		GetPaperResponse paper2 = new GetPaperResponse(2L, "Paper 2", "Content 2", BigDecimal.valueOf(2.99));
		GetAllPaperResponse paperResponse = new GetAllPaperResponse(Arrays.asList(paper1, paper2));

		GetBookOrderGetBookResponse bookResponse = new GetBookOrderGetBookResponse(
			"Dummy Book Title",
			BigDecimal.valueOf(19.99),
			"This is a dummy book description.",
			1L // dummy bookId
		);

		// Create dummy data for GetBookOrderResponse
		GetBookOrderResponse bookOrderResponse = new GetBookOrderResponse(
			bookResponse,
			1, // dummy quantity
			1L, // dummy orderListId
			123L // dummy orderId
		);

		// Mock the service methods
		when(paperTypeServiceImpl.getAllPaperTypes()).thenReturn(paperResponse);
		when(bookOrderServiceImpl.getBookOrder(anyLong())).thenReturn(bookOrderResponse);

		// Perform the request and validate the results
		mockMvc.perform(get("/api/orders/createOrderTestPaper/{order_list_id}", 1))
			.andExpect(status().isOk())
			.andExpect(view().name("order/selectPaper"))
			.andExpect(model().attributeExists("getAllPaperResponse"))
			.andExpect(model().attributeExists("orderListId"))
			.andExpect(model().attributeExists("bookOrder"));
	}

	@Test
	void testCreateOrderTestPaperPost() throws Exception {
		// Create dummy data for CreateOrderListPost
		CreateOrderListPost createOrderListPost = new CreateOrderListPost(Arrays.asList(1L, 2L));

		mockMvc.perform(post("/api/orders/createOrderTestPaper/{order_list_id}", 1)
				.flashAttr("createOrderListPost", createOrderListPost))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/deliveries/1"));
	}


	@Test
	void testCreateOrder() throws Exception {
		// 더미 데이터 생성
		GetBookOrderGetBookResponse bookResponse = new GetBookOrderGetBookResponse("Test Book", new BigDecimal("10.00"), "Test Description", 1L);
		GetBookOrderResponse bookOrderResponse = new GetBookOrderResponse(bookResponse, 1, 1L, 1L);

		GetWrappingResponse wrappingResponse1 = new GetWrappingResponse(1L, "Wrapping Paper 1", new BigDecimal("2.00"), 1);
		GetWrappingResponse wrappingResponse2 = new GetWrappingResponse(2L, "Wrapping Paper 2", new BigDecimal("3.00"), 2);
		GetListWrappingResponse wrappingResponse = new GetListWrappingResponse(Arrays.asList(wrappingResponse1, wrappingResponse2));

		GetUserPointOrderResponse pointResponse = new GetUserPointOrderResponse(new BigDecimal("100.00"));

		GetDeliveryPolicyResponse deliveryPolicyResponse = new GetDeliveryPolicyResponse(1L, "Standard Delivery", new BigDecimal("5.00"), "Delivery within 5 days", new BigDecimal("50.00"));

		// Mock 설정
		when(bookOrderServiceImpl.getBookOrder(anyLong())).thenReturn(bookOrderResponse);
		when(wrappingPaperServiceImpl.getWrappingPaperByOrderListId(anyLong())).thenReturn(wrappingResponse);
		when(orderServiceImpl.getUserPoint()).thenReturn(pointResponse);
		when(deliveryPolicyServiceImpl.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(anyLong(), any(BigDecimal.class)))
			.thenReturn(deliveryPolicyResponse);

		// 테스트 수행
		mockMvc.perform(get("/api/orders/createOrderTest/{order_list_id}/{delivery_id}", 1, 1))
			.andExpect(status().isOk())
			.andExpect(view().name("order/checkout"))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attributeExists("orderListId"))
			.andExpect(model().attributeExists("deliveryId"))
			.andExpect(model().attributeExists("wrappingList"))
			.andExpect(model().attributeExists("total"))
			.andExpect(model().attributeExists("delivery"))
			.andExpect(model().attributeExists("point"));
	}



	@Test
	void testCompleteOrder() throws Exception {
		// 더미 데이터 생성
		CreateOrderRequest createOrderRequest = new CreateOrderRequest(
			"John Doe",
			"john.doe@example.com",
			"01012345678",
			"123 Main St, Seoul",
			new BigDecimal("100.00"),
			new BigDecimal("10.00"),
			new BigDecimal("5.00"),
			1L
		);

		CreateOrderResponse createOrderResponse = new CreateOrderResponse(
			1L,
			"order123",
			new BigDecimal("85.00"),
			LocalDateTime.now(),
			new BigDecimal("10.00"),
			new BigDecimal("5.00")
		);

		// Mock 설정
		when(orderServiceImpl.createOrder(any(CreateOrderRequest.class))).thenReturn(createOrderResponse);
		doNothing().when(userAndCouponService).updateCouponAfterPayment(anyLong());
		when(deliveryServiceImpl.updateDeliveryAddOrder(anyLong(), anyLong())).thenReturn(null);
		when(bookOrderServiceImpl.updateOrder(anyLong(), anyLong())).thenReturn(null);

		// 테스트 수행
		mockMvc.perform(post("/api/orders/complete/{order_list_id}/{delivery_id}", 1, 1)
				.flashAttr("createOrderRequest", createOrderRequest))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/payments/order123"));
	}

	@Test
	void testOrderCheck() throws Exception {
		// 더미 데이터 생성
		List<GetAllOrderResponse> orderList = List.of(
			new GetAllOrderResponse(
				1L,
				LocalDateTime.now(),
				new BigDecimal("100.00"),
				"orderInfo123",
				"John Doe"
			),
			new GetAllOrderResponse(
				2L,
				LocalDateTime.now(),
				new BigDecimal("200.00"),
				"orderInfo456",
				"Jane Doe"
			)
		);
		GetAllListOrderResponse ordersResponse = new GetAllListOrderResponse(orderList);

		// Mock 설정
		when(orderServiceImpl.findAllUserId()).thenReturn(ordersResponse);

		// 테스트 수행
		mockMvc.perform(get("/api/orders/orderCheck"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/orderCheck"))
			.andExpect(model().attributeExists("orderList"));
	}

	@Test
	void testOrderCheckNon() throws Exception {
		mockMvc.perform(get("/api/orders/orderCheck/Non"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/orderCheckNon"));
	}

	@Test
	void testOrderDetails() throws Exception {
		// 주문 정보
		GetOrderByInfoResponse orderInfoResponse = GetOrderByInfoResponse.builder()
			.orderId(1L)
			.infoId("order123")
			.payername("John Doe")
			.payerEmail("johndoe@example.com")
			.payerAddress("123 Main St, Anytown")
			.orderDate(LocalDateTime.now())
			.status("Processed")
			.price(BigDecimal.valueOf(120.00))
			.couponSale(BigDecimal.valueOf(10.00))
			.pointSale(BigDecimal.valueOf(5.00))
			.build();

		// 배송 정보
		LocalDateTime deliveryTime = LocalDateTime.now().minusDays(1);
		GetDeliveryResponse deliveryResponse = GetDeliveryResponse.builder()
			.deliverySenderName("Online Store")
			.deliverySenderPhone("010-1234-5678")
			.deliverySenderDate(deliveryTime)
			.deliverySenderAddress("Warehouse #1, Industrial Park")
			.deliveryReceiver("John Doe")
			.deliveryReceiverPhone("010-9876-5432")
			.deliveryReceiverDate(deliveryTime)
			.deliveryReceiverAddress("123 Main St, Anytown")
			.orderId(1L)
			.deliveryStatusName("배송완료")
			.build();

		// 환불 정책 정보 설정
		GetAllRefundResponse allRefundResponse = GetAllRefundResponse.builder()
			.refunds(List.of(
				new GetRefundResponse(1L, "Refund within 30 days", 30)
			))
			.build();

		// Mock 서비스 호출 설정
		when(orderServiceImpl.findByOrderInfoId("order123")).thenReturn(orderInfoResponse);
		when(deliveryServiceImpl.getDeliveryByOrderId(1L)).thenReturn(deliveryResponse);
		when(refundPolicyServiceImpl.getAllRefundPolicies()).thenReturn(allRefundResponse);

		// 테스트 수행
		mockMvc.perform(get("/api/orders/orderDetails/{order_info_id}", "order123"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/orderDetails"))
			.andExpect(model().attributeExists("order"))
			.andExpect(model().attributeExists("delivery"))
			.andExpect(model().attributeExists("refundList")); // 확실히 환불 목록이 포함되어야 합니다.
	}

	@Test
	void testRefundAdmin() throws Exception {
		GetAllRefundResponse allRefunds = new GetAllRefundResponse(
			List.of(new GetRefundResponse(1L, "Refund within 30 days", 30)));
		when(refundPolicyServiceImpl.getAllRefundPolicies()).thenReturn(allRefunds);

		mockMvc.perform(get("/api/orders/admin/refundPolicy"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/adminRefund"))
			.andExpect(model().attributeExists("refundPolicy"));
	}

	@Test
	void testRefundAdminCreate() throws Exception {
		mockMvc.perform(get("/api/orders/admin/refundPolicy/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/createRefund"));
	}

	@Test
	void testRefundAdminUpdate() throws Exception {
		mockMvc.perform(get("/api/orders/admin/refundPolicy/update/{refundPolicyId}", 1L))
			.andExpect(status().isOk())
			.andExpect(view().name("order/updateRefund"))
			.andExpect(model().attributeExists("refundPolicyId"))
			.andExpect(model().attribute("refundPolicyId", 1L));
	}

	@Test
	void testPostRefundAdminCreate() throws Exception {
		mockMvc.perform(post("/api/orders/admin/refundPolicy")
				.param("refundPolicyContent", "New Policy")
				.param("refundPolicyDate", "15"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/refundPolicy"));
	}

	@Test
	void testPostRefundAdminUpdate() throws Exception {
		mockMvc.perform(post("/api/orders/admin/refundPolicy/{refundPolicyId}", 1L)
				.param("refundPolicyContent", "Updated Policy")
				.param("refundPolicyDate", "30"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/refundPolicy"));
	}

	@Test
	void testRefundAdminDelete() throws Exception {
		doNothing().when(refundPolicyServiceImpl).deleteRefundPolicy(1L);

		mockMvc.perform(get("/api/orders/admin/refundPolicy/{refundPolicyId}", 1L))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/refundPolicy"));
	}

	@Test
	void testAdminView() throws Exception {
		mockMvc.perform(get("/api/orders/admin"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/admin"));
	}

	@Test
	void testRefundingGet() throws Exception {
		String orderInfoId = "order123";
		doNothing().when(orderServiceImpl).refundingOrder(orderInfoId);

		mockMvc.perform(get("/api/orders/refunding/{orderInfoId}", orderInfoId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/orderDetails/" + orderInfoId));
	}

	@Test
	void testPaperAdminView() throws Exception {
		// 데이터 준비: GetAdminAllPaperResponse 객체 생성
		GetAdminAllPaperResponse getAllPaperResponse = GetAdminAllPaperResponse.builder()
			.papers(Arrays.asList(
				new GetPaperResponse(1L, "Standard Paper", "Description", new BigDecimal("1.99")),
				new GetPaperResponse(2L, "Premium Paper", "Description", new BigDecimal("3.99"))
			))
			.build();

		// 서비스가 이 데이터를 반환하도록 설정
		when(paperTypeServiceImpl.getAdminAllPaperTypes()).thenReturn(getAllPaperResponse);

		// 테스트 수행
		mockMvc.perform(get("/api/orders/admin/paper"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/adminPaper"))
			.andExpect(model().attributeExists("paper"))
			.andExpect(model().attribute("paper", getAllPaperResponse));
	}

	@Test
	void testCreatePaperAdmin() throws Exception {
		mockMvc.perform(get("/api/orders/admin/paper/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/createPaper"));
	}

	@Test
	void testUpdatePaperAdmin() throws Exception {
		Long paperTypeId = 1L;
		mockMvc.perform(get("/api/orders/admin/paper/update/{paperTypeId}", paperTypeId))
			.andExpect(status().isOk())
			.andExpect(view().name("order/updatePaper"))
			.andExpect(model().attributeExists("paperTypeId"))
			.andExpect(model().attribute("paperTypeId", paperTypeId));
	}

	@Test
	void testDeletePaperAdmin() throws Exception {
		Long paperTypeId = 1L;
		doNothing().when(paperTypeServiceImpl).deletePaperTypeById(paperTypeId);

		mockMvc.perform(get("/api/orders/admin/paper/{paperTypeId}", paperTypeId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/paper"));
	}

	@Test
	void testOrderStatusAdminViewWithOrders() throws Exception {
		// 데이터 준비: GetOrderStatusResponse 리스트 생성
		List<GetOrderStatusResponse> orderStatuses = Arrays.asList(
			GetOrderStatusResponse.builder()
				.orderStatusId(1L)
				.orderStatusName("Processed")
				.build(),
			GetOrderStatusResponse.builder()
				.orderStatusId(2L)
				.orderStatusName("Shipped")
				.build()
		);

		// 서비스가 이 데이터를 반환하도록 설정
		when(orderStatusServiceImpl.findAll()).thenReturn(orderStatuses);

		// 테스트 수행
		mockMvc.perform(get("/api/orders/admin/order-status"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/adminOrderStatus"))
			.andExpect(model().attributeExists("orderStatus"))
			.andExpect(model().attribute("orderStatus", orderStatuses)); // orderStatuses 리스트가 모델에 포함되어 있는지 확인
	}


	@Test
	void testCreateOrderStatusAdmin() throws Exception {
		mockMvc.perform(get("/api/orders/admin/order-status/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/createOrderStatus"));
	}

	@Test
	void testUpdateOrderStatusAdmin() throws Exception {
		Long orderStatusId = 1L;
		mockMvc.perform(get("/api/orders/admin/order-status/update/{orderStatusId}", orderStatusId))
			.andExpect(status().isOk())
			.andExpect(view().name("order/updateOrderStatus"))
			.andExpect(model().attributeExists("orderStatusId"))
			.andExpect(model().attribute("orderStatusId", orderStatusId));
	}

	@Test
	void testDeleteOrderStatusAdmin() throws Exception {
		Long orderStatusId = 1L;
		doNothing().when(orderStatusServiceImpl).delete(orderStatusId);

		mockMvc.perform(get("/api/orders/admin/order-status/{orderStatusId}", orderStatusId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/order-status"));
	}

	@Test
	void testCreateBookOrderWithInvalidInput() throws Exception {
		mockMvc.perform(post("/api/orders/createBookOrderTest")
				.param("bookId", "invalid")  // Invalid book ID
				.param("quantity", "-1"))   // Negative quantity
			.andExpect(status().isBadRequest());  // Expecting HTTP 400
	}


	@Test
	void testCreateOrderForMember() throws Exception {
		// 쿠폰 응답 객체 설정
		UserAndCouponOrderResponseDTO couponResponse = new UserAndCouponOrderResponseDTO(
			1L,                    // id
			BigDecimal.valueOf(50), // minOrderPrice
			BigDecimal.valueOf(10), // salePrice
			BigDecimal.valueOf(10), // saleRate
			BigDecimal.valueOf(20), // maxSalePrice
			"Percentage"            // type
		);

		// 책 주문 응답 객체 설정
		GetBookOrderResponse bookOrder = new GetBookOrderResponse(
			new GetBookOrderGetBookResponse("Test Book", BigDecimal.valueOf(100), "Description", 1L),
			1, // quantity
			1L, // orderListId
			1L  // orderId
		);

		// 포장지 응답 객체 설정
		List<GetWrappingResponse> wrappingList = List.of(
			new GetWrappingResponse(1L, "Standard Wrap", BigDecimal.valueOf(2), 2));
		GetListWrappingResponse wrappingResponse = new GetListWrappingResponse(wrappingList);

		// Mock 서비스 호출 설정
		when(bookOrderServiceImpl.getBookOrder(anyLong())).thenReturn(bookOrder);
		when(userAndCouponService.getSelectedCouponByOrder(anyLong())).thenReturn(couponResponse);
		when(wrappingPaperServiceImpl.getWrappingPaperByOrderListId(anyLong())).thenReturn(wrappingResponse);

		// 배송 정책 응답 객체 설정
		GetDeliveryPolicyResponse deliveryPolicy = new GetDeliveryPolicyResponse(1L, "Standard Delivery", BigDecimal.valueOf(5.00), "Delivery within 5 days", BigDecimal.valueOf(50.00));
		when(deliveryPolicyServiceImpl.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(anyLong(), any())).thenReturn(deliveryPolicy);

		// 테스트 수행
		mockMvc.perform(get("/api/orders/createOrderTest/{order_list_id}/{delivery_id}?couponId=1", 1, 1))
			.andExpect(status().isOk())
			.andExpect(view().name("order/checkout"))
			.andExpect(model().attributeExists("selectCoupon"))
			.andExpect(model().attribute("selectCoupon", couponResponse))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attributeExists("wrappingList"))
			.andExpect(model().attributeExists("delivery"));
	}



	@Test
	void testCreateOrderForNonMember() throws Exception {
		// 책 주문 응답 객체 설정
		GetBookOrderResponse bookOrder = new GetBookOrderResponse(
			new GetBookOrderGetBookResponse("Test Book", BigDecimal.valueOf(100), "Description", 1L),
			1, // quantity
			1L, // orderListId
			1L  // orderId
		);

		// 포장지 응답 객체 설정
		List<GetWrappingResponse> wrappingList = List.of(
			new GetWrappingResponse(1L, "Standard Wrap", BigDecimal.valueOf(2), 2));
		GetListWrappingResponse wrappingResponse = new GetListWrappingResponse(wrappingList);

		// Mock 서비스 호출 설정
		when(bookOrderServiceImpl.getBookOrder(anyLong())).thenReturn(bookOrder);
		when(wrappingPaperServiceImpl.getWrappingPaperByOrderListId(anyLong())).thenReturn(wrappingResponse);

		// 배송 정책 응답 객체 설정
		GetDeliveryPolicyResponse deliveryPolicy = new GetDeliveryPolicyResponse(1L, "Standard Delivery", BigDecimal.valueOf(5.00), "Delivery within 5 days", BigDecimal.valueOf(50.00));
		when(deliveryPolicyServiceImpl.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(anyLong(), any())).thenReturn(deliveryPolicy);

		// Mock getUserPoint 호출 설정
		GetUserPointOrderResponse pointResponse = new GetUserPointOrderResponse(BigDecimal.valueOf(10.00));
		when(orderServiceImpl.getUserPoint()).thenReturn(pointResponse);

		// NoCouponResponseDTO 설정
		NoCouponResponseDTO noCouponResponse = new NoCouponResponseDTO(
			BigDecimal.valueOf(100), // orderPrice
			BigDecimal.valueOf(10), // discount
			BigDecimal.valueOf(90) // orderPriceBeforePoint
		);
		when(userAndCouponService.noCouponReturnModel(any(), any(), any())).thenReturn(noCouponResponse);

		// 테스트 수행
		mockMvc.perform(get("/api/orders/createOrderTest/{order_list_id}/{delivery_id}", 1, 1))
			.andExpect(status().isOk())
			.andExpect(view().name("order/checkout"))
			.andExpect(model().attributeExists("noResultByNone"))
			.andExpect(model().attribute("noResultByNone", noCouponResponse))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attribute("orderList", bookOrder))
			.andExpect(model().attributeExists("orderListId"))
			.andExpect(model().attribute("orderListId", 1L))
			.andExpect(model().attributeExists("deliveryId"))
			.andExpect(model().attribute("deliveryId", 1L))
			.andExpect(model().attributeExists("wrappingList"))
			.andExpect(model().attribute("wrappingList", wrappingResponse))
			.andExpect(model().attributeExists("total"))
			.andExpect(model().attributeExists("delivery"))
			.andExpect(model().attribute("delivery", deliveryPolicy))
			.andExpect(model().attributeExists("point"))
			.andExpect(model().attribute("point", pointResponse))
			.andExpect(model().attributeDoesNotExist("selectCoupon"));
	}




	@Test
	void testUpdateAndDeletePaperAdmin() throws Exception {
		// Update paper type with all required parameters
		mockMvc.perform(post("/api/orders/admin/paper/{paperTypeId}", 1)
				.param("paperName", "Updated Name")
				.param("paperContent", "Updated Content") // This was missing
				.param("paperPrice", "2.50"))  // Ensure this matches the expected format and constraints
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/paper"));

		// Delete paper type
		mockMvc.perform(get("/api/orders/admin/paper/{paperTypeId}", 1))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/paper"));
	}


	@Test
	void testAdminViewsAndActions() throws Exception {
		// Testing admin view access
		mockMvc.perform(get("/api/orders/admin/order-status"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/adminOrderStatus"));

		// Testing create order status with valid data
		mockMvc.perform(post("/api/orders/admin/order-status")
				.param("orderStatusName", "New Status"))  // Ensure this parameter name matches the one expected by the controller
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/order-status"));

		// Testing update view for order status
		mockMvc.perform(get("/api/orders/admin/order-status/update/{orderStatusId}", 1))
			.andExpect(status().isOk())
			.andExpect(view().name("order/updateOrderStatus"));
	}

	@Test
	void testOrderCheckNonPost() throws Exception {
		// OrderCheckNonRequest 객체 생성
		OrderCheckNonRequest orderCheckNonRequest = new OrderCheckNonRequest("john.doe@example.com", "order123");

		// GetNonOrderByInfoResponse 객체 생성
		GetNonOrderByInfoResponse nonOrderByInfoResponse = new GetNonOrderByInfoResponse(
			1L,
			LocalDateTime.now(),
			new BigDecimal("100.00"),
			"order123",
			"John Doe"
		);

		// Mock 설정
		when(orderServiceImpl.findByOrderInfoIdByEmail(any(OrderCheckNonRequest.class))).thenReturn(nonOrderByInfoResponse);

		// 테스트 수행
		mockMvc.perform(post("/api/orders/orderCheck/Non")
				.flashAttr("orderCheckNonRequest", orderCheckNonRequest))
			.andExpect(status().isOk())
			.andExpect(view().name("order/orderDetailNon"))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attribute("orderList", nonOrderByInfoResponse));
	}

	@Test
	void testPostRefundAdminCreateWithInvalidInput() throws Exception {
		mockMvc.perform(post("/api/orders/admin/refundPolicy")
				.param("refundPolicyContent", "")  // Empty content
				.param("refundPolicyDate", "-1"))  // Invalid date
			.andExpect(status().isBadRequest());
	}

	@Test
	void testPostRefundAdminUpdateWithInvalidInput() throws Exception {
		mockMvc.perform(post("/api/orders/admin/refundPolicy/{refundPolicyId}", 1L)
				.param("refundPolicyContent", "")  // Empty content
				.param("refundPolicyDate", "-1"))  // Invalid date
			.andExpect(status().isBadRequest());
	}

	@Test
	void testUpdatePaperAdminWithInvalidInput() throws Exception {
		mockMvc.perform(post("/api/orders/admin/paper/{paperTypeId}", 1)
				.param("paperName", "")  // Empty name
				.param("paperContent", "Updated Content")
				.param("paperPrice", "-2.50"))  // Negative price
			.andExpect(status().isBadRequest());
	}

	////

	@Test
	void testCreateOrderTest() throws Exception {
		GetBookOrderResponse bookOrderResponse = new GetBookOrderResponse(
			new GetBookOrderGetBookResponse("Dummy Book Title", BigDecimal.valueOf(19.99), "This is a dummy book description.", 1L),
			1, // quantity
			1L, // orderListId
			123L // orderId
		);

		GetDeliveryPolicyResponse deliveryPolicyResponse = new GetDeliveryPolicyResponse(1L, "Standard Delivery", BigDecimal.valueOf(5.00), "Delivery within 5 days", BigDecimal.valueOf(50.00));

		GetWrappingResponse wrappingResponse = new GetWrappingResponse(1L, "Standard Wrap", BigDecimal.valueOf(2), 2);
		GetListWrappingResponse listWrappingResponse = new GetListWrappingResponse(Arrays.asList(wrappingResponse));

		GetUserPointOrderResponse pointResponse = new GetUserPointOrderResponse(BigDecimal.valueOf(10.00));

		// Mock 설정
		when(bookOrderServiceImpl.getBookOrder(anyLong())).thenReturn(bookOrderResponse);
		when(deliveryPolicyServiceImpl.findByDeliveryPolicyStandardPriceLessThanEqualOrderByDeliveryPolicyStandardPriceDesc(anyLong(), any())).thenReturn(deliveryPolicyResponse);
		when(wrappingPaperServiceImpl.getWrappingPaperByOrderListId(anyLong())).thenReturn(listWrappingResponse);
		when(orderServiceImpl.getUserPoint()).thenReturn(pointResponse);

		mockMvc.perform(get("/api/orders/createOrderTest/{order_list_id}/{delivery_id}", 1, 1))
			.andExpect(status().isOk())
			.andExpect(view().name("order/checkout"))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attributeExists("orderListId"))
			.andExpect(model().attributeExists("deliveryId"))
			.andExpect(model().attributeExists("wrappingList"))
			.andExpect(model().attributeExists("total"))
			.andExpect(model().attributeExists("delivery"))
			.andExpect(model().attributeExists("point"));
	}


	@Test
	void testRefundedGet() throws Exception {
		doNothing().when(orderServiceImpl).refundedOrder(anyString());

		mockMvc.perform(get("/api/orders/admin/refunded/{orderInfoId}", "order123"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/orderDetails/order123"));
	}


	@Test
	void testOrderStatusAdminView() throws Exception {
		// 데이터 준비: GetOrderStatusResponse 리스트 생성
		List<GetOrderStatusResponse> orderStatuses = Arrays.asList(
			new GetOrderStatusResponse(1L, "Processed"),
			new GetOrderStatusResponse(2L, "Shipped")
		);

		// 서비스가 이 데이터를 반환하도록 설정
		when(orderStatusServiceImpl.findAll()).thenReturn(orderStatuses);

		// 테스트 수행
		mockMvc.perform(get("/api/orders/admin/order-status"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/adminOrderStatus"))
			.andExpect(model().attributeExists("orderStatus"))
			.andExpect(model().attribute("orderStatus", orderStatuses));
	}

	@Test
	void testCartOrder() throws Exception {
		CreateCartOrderResponse cartOrderResponse = new CreateCartOrderResponse(1L,"orderInfoId123");

		when(orderServiceImpl.createCartOrder()).thenReturn(cartOrderResponse);
		when(bookCartService.getBookCartsByCartId(anyString(), any())).thenReturn(List.of(
			new GetBookCartResponse(1L, "testCartId", "imageUrl", "Test Book", "Author", "Publisher",
				BigDecimal.valueOf(10.00), BigDecimal.valueOf(8.00), BigDecimal.valueOf(20), 10, 1)
		));

		mockMvc.perform(get("/api/orders/cart-order").cookie(new Cookie("cartId", "testCartId")))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/cart-order/wrapping/orderInfoId123"));
	}

	@Test
	void testCartOrderWrappingGet() throws Exception {
		List<GetBookOrderResponse> bookOrders = List.of(
			new GetBookOrderResponse(
				new GetBookOrderGetBookResponse("Test Book", BigDecimal.valueOf(10.00), "Description", 1L),
				1, 1L, 1L
			)
		);

		GetAllPaperResponse allPaperResponse = new GetAllPaperResponse(List.of(
			new GetPaperResponse(1L, "Standard Paper", "Description", BigDecimal.valueOf(1.99))
		));

		when(bookOrderServiceImpl.getBookOrderByOrderId(anyString())).thenReturn(bookOrders);
		when(paperTypeServiceImpl.getAllPaperTypes()).thenReturn(allPaperResponse);

		mockMvc.perform(get("/api/orders/cart-order/wrapping/{orderInfoId}", "orderInfoId123"))
			.andExpect(status().isOk())
			.andExpect(view().name("cart-order/selectPaper"))
			.andExpect(model().attributeExists("getAllPaperResponse"))
			.andExpect(model().attributeExists("orderInfoId"))
			.andExpect(model().attributeExists("bookOrder"));
	}

	@Test
	void testCartOrderWrappingPost() throws Exception {
		// Create a valid CreateCartOrderPost object with non-null paperId
		CreateCartOrderPost createCartOrderPost = new CreateCartOrderPost(Arrays.asList(1L, 2L));

		// Mock 서비스 호출 설정
		List<GetBookOrderResponse> bookOrders = Arrays.asList(
			new GetBookOrderResponse(new GetBookOrderGetBookResponse("Test Book 1", BigDecimal.valueOf(10), "Description 1", 1L), 1, 1L, 1L),
			new GetBookOrderResponse(new GetBookOrderGetBookResponse("Test Book 2", BigDecimal.valueOf(20), "Description 2", 2L), 2, 2L, 2L)
		);

		when(bookOrderServiceImpl.getBookOrderByOrderId("orderInfoId123")).thenReturn(bookOrders);

		// Perform the test and expect a redirection
		mockMvc.perform(post("/api/orders/cart-order/wrapping/{orderInfoId}", "orderInfoId123")
				.flashAttr("createCartOrderPost", createCartOrderPost))  // ensure the flash attribute name matches the expected parameter name in the controller
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/deliveries/cart-order/orderInfoId123"));
	}


	@Test
	void testCompleteCartOrder() throws Exception {
		List<GetBookOrderResponse> bookOrders = List.of(
			new GetBookOrderResponse(
				new GetBookOrderGetBookResponse("Test Book", BigDecimal.valueOf(10.00), "Description", 1L),
				1, 1L, 1L
			)
		);

		CreateOrderRequest createOrderRequest = new CreateOrderRequest(
			"John Doe", "john.doe@example.com", "01012345678", "123 Main St", BigDecimal.valueOf(100.00),
			BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00), 1L
		);

		CreateOrderResponse createOrderResponse = new CreateOrderResponse(
			1L, "order123", BigDecimal.valueOf(85.00), LocalDateTime.now(), BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00)
		);

		when(bookOrderServiceImpl.getBookOrderByOrderId(anyString())).thenReturn(bookOrders);
		when(orderServiceImpl.updateCartOrder(any(CreateOrderRequest.class), anyLong())).thenReturn(createOrderResponse);

		mockMvc.perform(post("/api/orders/complete/cart-order/{orderInfoId}/{delivery_id}", "orderInfoId123", 1L)
				.flashAttr("createOrderRequest", createOrderRequest))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/payments/order123"));
	}

	@Test
	void testCreateOrderStatusAdminPost() throws Exception {
		CreateOrderStatusRequest createOrderStatusRequest = new CreateOrderStatusRequest("New Status");

		mockMvc.perform(post("/api/orders/admin/order-status")
				.flashAttr("createOrderStatusRequest", createOrderStatusRequest))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/order-status"));
	}

	@Test
	void testUpdateOrderStatusAdminPost() throws Exception {
		// CreateOrderStatusRequest with a valid orderStatusName
		CreateOrderStatusRequest createOrderStatusRequest = new CreateOrderStatusRequest("Updated");

		mockMvc.perform(post("/api/orders/admin/order-status/{orderStatusId}", 1L)
				.flashAttr("createOrderStatusRequest", createOrderStatusRequest))  // ensure the flash attribute name matches the expected parameter name in the controller
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/order-status"));
	}


	@Test
	void testRefundAdminDeleteWithValidId() throws Exception {
		Long refundPolicyId = 1L;
		doNothing().when(refundPolicyServiceImpl).deleteRefundPolicy(refundPolicyId);

		mockMvc.perform(get("/api/orders/admin/refundPolicy/{refundPolicyId}", refundPolicyId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/refundPolicy"));
	}

	@Test
	void testRefundAdminCreatePost() throws Exception {
		// Create a valid CreateRefundPolicyRequest object
		CreateRefundPolicyRequest createRefundPolicyRequest = new CreateRefundPolicyRequest("New Policy", 15);

		// Perform the test and expect a redirection
		mockMvc.perform(post("/api/orders/admin/refundPolicy")
				.flashAttr("createRefundPolicyRequest", createRefundPolicyRequest))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/refundPolicy"));
	}

	@Test
	void testRefundAdminUpdatePost() throws Exception {
		// Create a valid UpdateRefundPolicyRequest object
		UpdateRefundPolicyRequest updateRefundPolicyRequest = new UpdateRefundPolicyRequest("Updated Policy", 30);

		// Perform the test and expect a redirection
		mockMvc.perform(post("/api/orders/admin/refundPolicy/{refundPolicyId}", 1L)
				.flashAttr("updateRefundPolicyRequest", updateRefundPolicyRequest))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/refundPolicy"));
	}

	@Test
	void testPaperAdminCreatePost() throws Exception {
		// Create a valid CreateWrappingTypeRequest object
		CreateWrappingTypeRequest createWrappingTypeRequest = new CreateWrappingTypeRequest("New Paper", "Description", new BigDecimal("2.50"));

		// Perform the test and expect a redirection
		mockMvc.perform(post("/api/orders/admin/paper")
				.flashAttr("createWrappingTypeRequest", createWrappingTypeRequest))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/paper"));
	}

	@Test
	void testPaperAdminUpdatePost() throws Exception {
		// Create a valid UpdateWrappingTypeRequest object
		UpdateWrappingTypeRequest updateWrappingTypeRequest = new UpdateWrappingTypeRequest("Updated Paper", "Updated Description", new BigDecimal("3.00"));

		// Perform the test and expect a redirection
		mockMvc.perform(post("/api/orders/admin/paper/{paperTypeId}", 1L)
				.flashAttr("updateWrappingTypeRequest", updateWrappingTypeRequest))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/orders/admin/paper"));
	}

	@Test
	void testOrderStatusWait() throws Exception {
		// Create dummy data
		GetAllListOrderByStatusResponse ordersResponse = new GetAllListOrderByStatusResponse(List.of());

		// Mock the service method
		when(orderServiceImpl.findByOrderStatusWait()).thenReturn(ordersResponse);

		// Perform the test
		mockMvc.perform(get("/api/orders/admin/order-status/wait"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/wait"))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attribute("orderList", ordersResponse));
	}

	@Test
	void testOrderStatusGoing() throws Exception {
		// Create dummy data
		GetAllListOrderByStatusResponse ordersResponse = new GetAllListOrderByStatusResponse(List.of());

		// Mock the service method
		when(orderServiceImpl.findByOrderStatusGoing()).thenReturn(ordersResponse);

		// Perform the test
		mockMvc.perform(get("/api/orders/admin/order-status/going"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/going"))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attribute("orderList", ordersResponse));
	}

	@Test
	void testOrderStatusComplete() throws Exception {
		// Create dummy data
		GetAllListOrderByStatusResponse ordersResponse = new GetAllListOrderByStatusResponse(List.of());

		// Mock the service method
		when(orderServiceImpl.findByOrderStatusComplete()).thenReturn(ordersResponse);

		// Perform the test
		mockMvc.perform(get("/api/orders/admin/order-status/complete"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/complete"))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attribute("orderList", ordersResponse));
	}

	@Test
	void testOrderStatusRefunded() throws Exception {
		// Create dummy data
		GetAllListOrderByStatusResponse ordersResponse = new GetAllListOrderByStatusResponse(List.of());

		// Mock the service method
		when(orderServiceImpl.findByOrderStatusRefunded()).thenReturn(ordersResponse);

		// Perform the test
		mockMvc.perform(get("/api/orders/admin/order-status/refunded"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/refunded"))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attribute("orderList", ordersResponse));
	}

	@Test
	void testOrderStatusRefunding() throws Exception {
		// Create dummy data
		GetAllListOrderByStatusResponse ordersResponse = new GetAllListOrderByStatusResponse(List.of());

		// Mock the service method
		when(orderServiceImpl.findByOrderStatusRefunding()).thenReturn(ordersResponse);

		// Perform the test
		mockMvc.perform(get("/api/orders/admin/order-status/refunding"))
			.andExpect(status().isOk())
			.andExpect(view().name("order/refunding"))
			.andExpect(model().attributeExists("orderList"))
			.andExpect(model().attribute("orderList", ordersResponse));
	}

}
