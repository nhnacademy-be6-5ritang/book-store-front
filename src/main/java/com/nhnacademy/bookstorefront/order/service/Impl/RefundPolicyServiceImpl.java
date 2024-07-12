package com.nhnacademy.bookstorefront.order.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstorefront.order.dto.request.CreateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.request.UpdateRefundPolicyRequest;
import com.nhnacademy.bookstorefront.order.dto.response.GetAllRefundResponse;
import com.nhnacademy.bookstorefront.order.feignclient.OrderServiceClient;
import com.nhnacademy.bookstorefront.order.service.RefundPolicyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class RefundPolicyServiceImpl implements RefundPolicyService {

	private final OrderServiceClient orderServiceClient;
	@Override
	public void createRefundPolicy(CreateRefundPolicyRequest request) {
		orderServiceClient.createRefundPolicy(request).getBody();
	}

	@Override
	public void updateRefundPolicy(UpdateRefundPolicyRequest request, Long refundPolicyId) {
		orderServiceClient.updateRefundPolicy(refundPolicyId, request).getBody();
	}

	@Override
	public void deleteRefundPolicy(Long refundPolicyId) {
		orderServiceClient.deleteRefundPolicy(refundPolicyId).getBody();
	}

	@Override
	public GetAllRefundResponse getAllRefundPolicies() {
		return orderServiceClient.getRefundPolicy().getBody();
	}
}
