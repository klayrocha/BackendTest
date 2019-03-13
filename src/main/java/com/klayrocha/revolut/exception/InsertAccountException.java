package com.klayrocha.revolut.exception;

/**
 * Exception class when adding the Account
 * @author klayrocha
 *
 */
public class InsertAccountException extends RuntimeException {

	private static final long serialVersionUID = -1733900275340573427L;

	public InsertAccountException(String message) {
		super(message);
	}
}
