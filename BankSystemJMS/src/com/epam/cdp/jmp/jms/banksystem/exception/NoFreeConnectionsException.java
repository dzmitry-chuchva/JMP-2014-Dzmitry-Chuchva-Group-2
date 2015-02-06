package com.epam.cdp.jmp.jms.banksystem.exception;

public class NoFreeConnectionsException extends TechnicalException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6817356655940162330L;

	public NoFreeConnectionsException() {
	}

	public NoFreeConnectionsException(String message) {
		super(message);
	}
}
