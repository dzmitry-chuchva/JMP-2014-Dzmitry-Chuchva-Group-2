package com.epam.cdp.jmp.jms.banksystem.exception;

public class TechnicalException extends RuntimeException {
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
