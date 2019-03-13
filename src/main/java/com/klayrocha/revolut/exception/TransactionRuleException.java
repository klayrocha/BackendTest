package com.klayrocha.revolut.exception;

/**
 * Exception class of rules in transfer
 * @author klayrocha
 *
 */
public class TransactionRuleException extends RuntimeException {

	private static final long serialVersionUID = 7324177176164731551L;

	public TransactionRuleException(String message) {
		super(message);
	}
}
