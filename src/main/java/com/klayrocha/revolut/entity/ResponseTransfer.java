package com.klayrocha.revolut.entity;

public class ResponseTransfer {

	private Account accountCredit;
	private Account accountDebit;

	public Account getAccountCredit() {
		return accountCredit;
	}

	public void setAccountCredit(Account accountCredit) {
		this.accountCredit = accountCredit;
	}

	public Account getAccountDebit() {
		return accountDebit;
	}

	public void setAccountDebit(Account accountDebit) {
		this.accountDebit = accountDebit;
	}

}
