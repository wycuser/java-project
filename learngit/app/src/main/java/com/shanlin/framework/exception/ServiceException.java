package com.shanlin.framework.exception;

import com.shanlin.framework.controller.InterfaceResultCode;

public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String errorCode = InterfaceResultCode.FAILED;
	
	public ServiceException(Exception e) {
		super(e);
	}
	
	public ServiceException(String errorString){
		super(errorString);
	}
	
	public ServiceException(String errorCode, String errorString) {
		super(errorString);
		this.errorCode = errorCode;
	}
	
	public ServiceException(String errorCode, String errorString, Throwable cause) {
		super(errorString, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getErrorString() {
		return this.getMessage();
	}
}
