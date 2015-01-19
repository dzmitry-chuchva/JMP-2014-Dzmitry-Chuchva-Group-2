package com.epam.cdp.java.banksystem.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class BanksystemExceptionHandler {
	private final String ERROR_PAGE = "error";

	@ExceptionHandler(Exception.class)
	public ModelAndView defaultErrorHandler(Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", e.getMessage());
		mav.setViewName(ERROR_PAGE);
		return mav;
	}
}
