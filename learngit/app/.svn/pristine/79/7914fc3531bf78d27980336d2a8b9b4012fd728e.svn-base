package com.shanlin.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.framework.exception.ServiceException;

/**
 * spring mvc 统一异常处理类
 * @author zheng xin
 * @createDate 2015年1月28日
 */
@ControllerAdvice
public class ErrorActionAdvice {
	
	private static Logger log = LoggerFactory.getLogger(ErrorActionAdvice.class);
	
	@ExceptionHandler(Throwable.class)
	public @ResponseBody String exceptionHandler(Throwable error, HttpServletRequest request, HttpServletResponse response){
		String mess = null;
		if(error instanceof IllegalArgumentException || error instanceof MissingServletRequestParameterException){
			mess = error.getMessage();
			request.setAttribute("adviceCode", InterfaceResultCode.FAILED);
			log.error("error", mess);
		} else if(error instanceof ServiceException){
			mess = error.getMessage();
			ServiceException se = (ServiceException) error;
			request.setAttribute("adviceCode", se.getErrorCode());
			if(InterfaceResultCode.SYS_ERROR.equals(se.getErrorCode())){
				log.error("error", error);
			}else{
				log.error("error", mess);
			}
		} else {
			mess = "系统异常";
			request.setAttribute("adviceCode", InterfaceResultCode.SYS_ERROR);
			log.error("error", error);
		}
	    return mess;
	}
	
}
