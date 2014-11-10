package com.epam.cdp.java.banksystem.exception;

public class TechnicalException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9061155347574851204L;

	public TechnicalException() {
	}

	public TechnicalException(String message) {
		super(message);
	}

	public TechnicalException(Throwable e) {
		super(e);
	}
}
