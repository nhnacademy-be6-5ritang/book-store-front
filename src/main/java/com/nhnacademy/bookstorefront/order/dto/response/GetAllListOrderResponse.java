package com.nhnacademy.bookstorefront.order.dto.response;

import java.util.List;

public record GetAllListOrderResponse(
	List<GetAllOrderResponse> orders
) {}