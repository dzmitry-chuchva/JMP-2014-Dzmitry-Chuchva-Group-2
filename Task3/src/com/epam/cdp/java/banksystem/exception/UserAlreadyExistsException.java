package com.epam.cdp.java.banksystem.exception;

public class UserAlreadyExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5705984051378607552L;

	public UserAlreadyExistsException() {
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
