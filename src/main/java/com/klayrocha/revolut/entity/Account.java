package com.klayrocha.revolut.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.klayrocha.revolut.service.EntityLockWrapper;

/**
 * Class representing the model of an account
 * 
 * @author klayrocha
 *
 */
public class Account implements Serializable, EntityLockWrapper.Lockable {

	private static final long serialVersionUID = 4884271070280691873L;

	@NotNull(message = "The number must be informed")
	private BigInteger number;

	@NotNull(message = "The ownerAccount must be informed")
	@Size(min = 5, message = "The minimum size of the ownerAccount is 5")
	private String ownerAccount;

	@NotNull(message = "The iban must be informed")
	@Size(min = 20, max = 20, message = "The size of the iban should be 20")
	private String iban;

	@NotNull(message = "The bic must be informed")
	@Size(min = 8, max = 8, message = "The size of the iban should be 8")
	private String bic;

	@NotNull(message = "The balance must be informed")
	private BigDecimal balance;

	@JsonIgnoreProperties("account")
	private List<Transaction> transactions;

	@JsonIgnore
	private ReentrantLock lock = new ReentrantLock();
	
	public Account() {
		super();
	}

	public Account(BigInteger number, String ownerAccount, String iban, String bic, BigDecimal balance) {
		super();
		this.number = number;
		this.ownerAccount = ownerAccount;
		this.iban = iban;
		this.bic = bic;
		this.balance = balance;
	}

	public BigInteger getNumber() {
		return number;
	}

	public void setNumber(BigInteger number) {
		this.number = number;
	}

	public String getOwnerAccount() {
		return ownerAccount;
	}

	public void setOwnerAccount(String ownerAccount) {
		this.ownerAccount = ownerAccount;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		if (this.transactions == null) {
			this.transactions = new ArrayList<Transaction>();
		}
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	@Override
	public ReentrantLock getLock() {
		return lock;
	}

	@Override
	public String toString() {
		return String.format("Account [number=%s, ownerAccount=%s, iban=%s, bic=%s, balance=%s, transactions=%s]",
				number, ownerAccount, iban, bic, balance, transactions);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((bic == null) ? 0 : bic.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((ownerAccount == null) ? 0 : ownerAccount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (bic == null) {
			if (other.bic != null)
				return false;
		} else if (!bic.equals(other.bic))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (ownerAccount == null) {
			if (other.ownerAccount != null)
				return false;
		} else if (!ownerAccount.equals(other.ownerAccount))
			return false;
		return true;
	}

}
