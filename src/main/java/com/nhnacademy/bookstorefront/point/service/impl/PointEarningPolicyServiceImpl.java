package com.nhnacademy.bookstorefront.point.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.bookstorefront.point.dto.request.CreatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.request.UpdatePointEarningPolicyRequest;
import com.nhnacademy.bookstorefront.point.dto.response.CreatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.dto.response.GetPointTransactionResponse;
import com.nhnacademy.bookstorefront.point.dto.response.UpdatePointEarningPolicyResponse;
import com.nhnacademy.bookstorefront.point.feignclient.PointServiceClient;
import com.nhnacademy.bookstorefront.point.service.PointEarningPolicyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointEarningPolicyServiceImpl implements PointEarningPolicyService {
	private final PointServiceClient pointServiceClient;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public CreatePointEarningPolicyResponse createPointEarningPolicy(
		CreatePointEarningPolicyRequest createPointEarningPolicyRequest) {
		return pointServiceClient.createPointEarningPolicy(createPointEarningPolicyRequest).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<GetPointEarningPolicyResponse> getPointEarningPolicies() {
		return pointServiceClient.getPointEarningPolicies().getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public UpdatePointEarningPolicyResponse updatePointEarningPolicy(Long pointEarningPolicyId,
		UpdatePointEarningPolicyRequest updatePointEarningPolicyRequest) {
		return pointServiceClient.updatePointEarningPolicy(pointEarningPolicyId, updatePointEarningPolicyRequest)
			.getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void activatePointEarningPolicy(Long pointEarningPolicyId) {
		pointServiceClient.activatePointEarningPolicy(pointEarningPolicyId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public void deactivatePointEarningPolicy(Long pointEarningPolicyId) {
		pointServiceClient.deactivatePointEarningPolicy(pointEarningPolicyId).getBody();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Page<GetPointTransactionResponse> getPointTransactions(Pageable pageable) {
		return pointServiceClient.getPointTransactions(pageable).getBody();
	}

}
