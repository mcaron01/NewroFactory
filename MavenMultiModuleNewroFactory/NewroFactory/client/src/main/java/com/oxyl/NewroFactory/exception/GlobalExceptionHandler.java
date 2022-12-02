package com.oxyl.NewroFactory.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	private static final String EXCEPTION_MESSAGE = "exception";
	
	@ExceptionHandler({InternException.class})
	public ModelAndView handleInternException(InternException e) {
		LOGGER.error("InternException", e);
		ModelAndView modelAndView = new ModelAndView("500");
		modelAndView.addObject(EXCEPTION_MESSAGE, e);
		return modelAndView;
	}
	
	@ExceptionHandler({PromotionException.class})
	public ModelAndView handlePromotionException(PromotionException e) {
		LOGGER.error("PromotionException", e);
		ModelAndView modelAndView = new ModelAndView("500");
		modelAndView.addObject(EXCEPTION_MESSAGE, e);
		return modelAndView;
	}
	
	@ExceptionHandler({UserException.class})
	public ModelAndView handleUserException(UserException e) {
		LOGGER.error("UserException", e);
		ModelAndView modelAndView = new ModelAndView("500");
		modelAndView.addObject(EXCEPTION_MESSAGE, e);
		return modelAndView;
	}

}
