package com.nhnacademy.bookstorefront.order.dto.response;

import java.util.List;

public record GetListWrappingResponse(
	List<GetWrappingResponse> wrapping
) {


}