package com.nhnacademy.bookstorefront.point;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nhnacademy.bookstorefront.cache.service.impl.CacheServiceImpl;
import com.nhnacademy.bookstorefront.point.controller.PointController;
import com.nhnacademy.bookstorefront.point.dto.request.CreatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.request.UpdatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.response.CreatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetAllPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.UpdatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.service.impl.PointEarningPolicyServiceImpl;
import com.nhnacademy.bookstorefront.point.service.impl.PointTransactionServiceImpl;

@WebMvcTest(PointController.class)
class PointControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private PointEarningPolicyServiceImpl pointEarningPolicyService;

	@MockBean
	private PointTransactionServiceImpl pointTransactionService;

	@MockBean
	private CacheServiceImpl cacheService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(
				new PointController(pointEarningPolicyService, pointTransactionService))
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	void testEarningPoliciesAdmin() throws Exception {
		List<GetPointEarningPolicyResponse> policies = List.of(
			new GetPointEarningPolicyResponse(1L, "PolicyType1", BigDecimal.valueOf(10), "ACTIVE"),
			new GetPointEarningPolicyResponse(2L, "PolicyType2", BigDecimal.valueOf(20), "INACTIVE")
		);

		when(pointEarningPolicyService.getPointEarningPolicies()).thenReturn(policies);

		mockMvc.perform(get("/api/point-earning-policies/admin"))
			.andExpect(status().isOk())
			.andExpect(view().name("point/policyAdmin"))
			.andExpect(model().attributeExists("policies"));
	}

	@Test
	void testActivatePointEarningPolicy() throws Exception {
		doNothing().when(pointEarningPolicyService).activatePointEarningPolicy(anyLong());

		List<GetPointEarningPolicyResponse> policies = List.of(
			new GetPointEarningPolicyResponse(1L, "PolicyType1", BigDecimal.valueOf(10), "ACTIVE")
		);

		when(pointEarningPolicyService.getPointEarningPolicies()).thenReturn(policies);

		mockMvc.perform(get("/api/point-earning-policies/admin/{policyId}/active", 1L))
			.andExpect(status().isOk())
			.andExpect(view().name("point/policyAdmin"))
			.andExpect(model().attributeExists("policies"));
	}

	@Test
	void testDeactivatePointEarningPolicy() throws Exception {
		doNothing().when(pointEarningPolicyService).deactivatePointEarningPolicy(anyLong());

		List<GetPointEarningPolicyResponse> policies = List.of(
			new GetPointEarningPolicyResponse(1L, "PolicyType1", BigDecimal.valueOf(10), "INACTIVE")
		);

		when(pointEarningPolicyService.getPointEarningPolicies()).thenReturn(policies);

		mockMvc.perform(get("/api/point-earning-policies/admin/{policyId}/deactivate", 1L))
			.andExpect(status().isOk())
			.andExpect(view().name("point/policyAdmin"))
			.andExpect(model().attributeExists("policies"));
	}

	@Test
	void testCreatePointEarningPolicyPage() throws Exception {
		mockMvc.perform(get("/api/point-earning-policies/admin/create"))
			.andExpect(status().isOk())
			.andExpect(view().name("point/create"));
	}

	@Test
	void testCreatePointEarningPolicy() throws Exception {
		CreatePointEarningPolicyRequest request = new CreatePointEarningPolicyRequest("PolicyType",
			BigDecimal.valueOf(10));

		when(pointEarningPolicyService.createPointEarningPolicy(any(CreatePointEarningPolicyRequest.class)))
			.thenReturn(new CreatePointEarningPolicyResponse("PolicyType", BigDecimal.valueOf(10)));

		mockMvc.perform(post("/api/point-earning-policies/admin")
				.flashAttr("createPointEarningPolicyRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/point-earning-policies/admin"));
	}

	@Test
	void testUpdatePointEarningPolicyPage() throws Exception {
		mockMvc.perform(get("/api/point-earning-policies/admin/{policyId}", 1L))
			.andExpect(status().isOk())
			.andExpect(view().name("point/update"))
			.andExpect(model().attributeExists("policyId"))
			.andExpect(model().attribute("policyId", 1L));
	}

	@Test
	void testUpdatePointEarningPolicy() throws Exception {
		UpdatePointEarningPolicyRequest request = new UpdatePointEarningPolicyRequest("UpdatedPolicy",
			BigDecimal.valueOf(20));

		when(pointEarningPolicyService.updatePointEarningPolicy(anyLong(), any(UpdatePointEarningPolicyRequest.class)))
			.thenReturn(new UpdatePointEarningPolicyResponse("UpdatedPolicy", BigDecimal.valueOf(20)));

		mockMvc.perform(post("/api/point-earning-policies/admin/{policyId}", 1L)
				.flashAttr("updatePointEarningPolicyRequest", request))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/api/point-earning-policies/admin"));
	}

	@Test
	void testTransactions() throws Exception {
		Pageable pageable = PageRequest.of(0, 10);
		List<GetPointTransactionResponse> transactions = List.of(
			new GetPointTransactionResponse("PolicyType", BigDecimal.valueOf(100), LocalDateTime.now())
		);
		Page<GetPointTransactionResponse> response = new PageImpl<>(transactions, pageable, transactions.size());

		when(pointTransactionService.getPointTransactions(any(Pageable.class))).thenReturn(response);

		mockMvc.perform(get("/api/point-transactions")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(view().name("point/transactions"))
			.andExpect(model().attributeExists("pointTransactions"));
	}

	@Test
	void testTransactionsAdmin() throws Exception {
		Pageable pageable = PageRequest.of(0, 10);
		List<GetAllPointTransactionResponse> transactions = List.of(
			new GetAllPointTransactionResponse(1L, 1L, BigDecimal.valueOf(100), LocalDateTime.now())
		);
		Page<GetAllPointTransactionResponse> response = new PageImpl<>(transactions, pageable, transactions.size());

		when(pointTransactionService.getAllPointTransactions(any(Pageable.class))).thenReturn(response);

		mockMvc.perform(get("/api/point-transactions/admin"))
			.andExpect(status().isOk())
			.andExpect(view().name("point/transactionsAdmin"))
			.andExpect(model().attributeExists("pointTransactions"));
	}

	@Test
	void testPointAdmin() throws Exception {
		mockMvc.perform(get("/api/point/admin"))
			.andExpect(status().isOk())
			.andExpect(view().name("point/admin"));
	}
}

