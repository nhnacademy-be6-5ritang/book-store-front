package com.nhnacademy.bookstorefront.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhnacademy.bookstorefront.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/create")
	public ModelAndView createReview(@PathVariable Long bookId) {
		ModelAndView modelAndView = new ModelAndView("/review/create-review");
		modelAndView.addObject("bookId", bookId);

		return modelAndView;
	}

	@GetMapping("/create2")
	public ModelAndView createReview2(@PathVariable Long bookId) {
		ModelAndView modelAndView = new ModelAndView("/review/create-review2");
		modelAndView.addObject("bookId", bookId);

		return modelAndView;
	}

	@GetMapping
	public ModelAndView getReviews(@PathVariable Long bookId) {
		ModelAndView modelAndView = new ModelAndView("/review/list-review");
		modelAndView.addObject("reviews", reviewService.getReviews(bookId));
		return modelAndView;
	}
}
