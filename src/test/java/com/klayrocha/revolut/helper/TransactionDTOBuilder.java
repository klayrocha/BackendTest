package com.klayrocha.revolut.helper;

import java.math.BigDecimal;

import com.klayrocha.revolut.entity.TransactionDTO;

/**
 * Class builder for TransactionDTO class
 * @author klayrocha
 *
 */
public class TransactionDTOBuilder {

	private TransactionDTO dto;

	private TransactionDTOBuilder() {
	}

	public static TransactionDTOBuilder oneTransactionDTO() {
		TransactionDTOBuilder builder = new TransactionDTOBuilder();
		builder.dto = new TransactionDTO();
		builder.dto.setIbanDebit("PT233920012223230888");
		builder.dto.setIbanCredit("PT111999992223230790");
		builder.dto.setValue(new BigDecimal(100));
		builder.dto.setDescription("Test JUnit");
		return builder;
	}

	public TransactionDTOBuilder valueZero() {
		dto.setValue(new BigDecimal(0));
		return this;
	}

	public TransactionDTOBuilder highValue() {
		dto.setValue(new BigDecimal(1000000));
		return this;
	}

	public TransactionDTOBuilder ibanDebitInvalid() {
		dto.setIbanDebit("PT233920012223230");
		return this;
	}

	public TransactionDTO now() {
		return dto;
	}

}
