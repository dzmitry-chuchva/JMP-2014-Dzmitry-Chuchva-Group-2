package com.epam.cdp.java.banksystem.exception;

public class ServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9061155347574851204L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}
}
