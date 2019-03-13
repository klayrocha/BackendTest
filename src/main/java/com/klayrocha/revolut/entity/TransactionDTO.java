package com.klayrocha.revolut.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Class representing the template to transfer transaction information
 * @author klayrocha
 *
 */
public class TransactionDTO implements Serializable {

	private static final long serialVersionUID = 718847014893878537L;

	private String ibanDebit;
	private String ibanCredit;
	private BigDecimal value;
	private String description;

	public String getIbanDebit() {
		return ibanDebit;
	}

	public void setIbanDebit(String ibanDebit) {
		this.ibanDebit = ibanDebit;
	}

	public String getIbanCredit() {
		return ibanCredit;
	}

	public void setIbanCredit(String ibanCredit) {
		this.ibanCredit = ibanCredit;
	}

	public BigDecimal getValue() {
		if(value == null) {
			value = new BigDecimal(0);
		}
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ibanCredit == null) ? 0 : ibanCredit.hashCode());
		result = prime * result + ((ibanDebit == null) ? 0 : ibanDebit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		TransactionDTO other = (TransactionDTO) obj;
		if (ibanCredit == null) {
			if (other.ibanCredit != null)
				return false;
		} else if (!ibanCredit.equals(other.ibanCredit))
			return false;
		if (ibanDebit == null) {
			if (other.ibanDebit != null)
				return false;
		} else if (!ibanDebit.equals(other.ibanDebit))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
