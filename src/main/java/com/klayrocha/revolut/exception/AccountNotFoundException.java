package com.klayrocha.revolut.exception;

/**
 * Exception class for Account not found
 * @author klayrocha
 *
 */
public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2415810825731126870L;

	public AccountNotFoundException(String message) {
		super(message);
	}
}
