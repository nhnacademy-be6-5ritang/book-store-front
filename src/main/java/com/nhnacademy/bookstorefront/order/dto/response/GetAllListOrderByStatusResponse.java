package com.nhnacademy.bookstorefront.order.dto.response;

import java.util.List;

public record GetAllListOrderByStatusResponse(
	List<GetAllOrderByStatusResponse> orders
) {
}
