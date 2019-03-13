package com.klayrocha.revolut.helper;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.klayrocha.revolut.entity.Account;

/**
 * Class builder for Accoun class
 * @author klayrocha
 *
 */
public class AccountBuilder {

	private Account account;

	private AccountBuilder() {
	}

	public static AccountBuilder oneAccount() {
		AccountBuilder builder = new AccountBuilder();
		builder.account = new Account();
		builder.account.setNumber(new BigInteger("3920012223230777"));
		builder.account.setOwnerAccount("James Brown");
		builder.account.setIban("PT233920012223230777");
		builder.account.setBic("BIMOMZMX");
		builder.account.setBalance(new BigDecimal(980));
		return builder;
	}
	
	public AccountBuilder ibanNull() {
		account.setIban(null);
		return this;
	}
	
	public AccountBuilder numberNull() {
		account.setNumber(null);
		return this;
	}
	
	public AccountBuilder ibanInvalid() {
		account.setIban("PT555");
		return this;
	}
	
	public Account now() {
		return account;
	}

}
