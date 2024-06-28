package com.nhnacademy.bookstorefront.order.dto.request;

import java.util.List;

public record CreateOrderListPost(
	List<Long> paperId,
	List<Integer> quantity
) {
}
