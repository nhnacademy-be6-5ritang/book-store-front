package com.nhnacademy.bookstorefront.order.dto.request;

import java.util.List;

public record CreateCartOrderPost(
	List<Long> paperId
) {
}
