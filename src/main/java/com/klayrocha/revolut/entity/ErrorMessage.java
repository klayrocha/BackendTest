package com.klayrocha.revolut.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class representing the template for error messages
 * @author klayrocha
 *
 */
@XmlRootElement
public class ErrorMessage {

	private String errorMessage;
	private int errorCode;

	public ErrorMessage() {

	}

	public ErrorMessage(String errorMessage, int errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
