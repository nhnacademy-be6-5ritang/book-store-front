package com.nhnacademy.bookstorefront.order.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record GetAdminAllPaperResponse(
	List<GetPaperResponse> papers
) {
}
