package com.epam.cdp.jmp.jms.banksystem.exception;

public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5705984051378607552L;

	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
