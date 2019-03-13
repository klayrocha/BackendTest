package com.klayrocha.revolut.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.klayrocha.revolut.entity.Account;
import com.klayrocha.revolut.entity.TransactionDTO;
import com.klayrocha.revolut.exception.AccountNotFoundException;
import com.klayrocha.revolut.exception.InsertAccountException;
import com.klayrocha.revolut.exception.TransactionRuleException;
import com.klayrocha.revolut.helper.AccountBuilder;
import com.klayrocha.revolut.helper.TransactionDTOBuilder;

/**
 * Test class for AccountService class
 * @author klayrocha
 *
 */
public class AccountServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private AccountService service = null;
	
	@Before
	public void setup() throws Exception {
		service = Mockito.spy(AccountService.class);
	}
	
	@Test
	public void testRecoverAccount() {
		Account account = service.findByIban("PT233920012223230888");
		assertEquals("PT233920012223230888", account.getIban());
	}
  
	@Test
	public void testInsertAccount() {
		Account account = AccountBuilder.oneAccount().now();
		service.insertAccount(account);
		Account accountRet = service.findByIban("PT233920012223230777");
		assertEquals("PT233920012223230777", accountRet.getIban());
	}

	
	@Test
	public void testInsertAccountIbanNull() {
		Account account = AccountBuilder.oneAccount().ibanNull().now();
		
		exception.expect(InsertAccountException.class);
		exception.expectMessage("The iban must be informed");
		
		service.insertAccount(account);
	}

	@Test
	public void testInsertAccountNumberNull() {
		Account account = AccountBuilder.oneAccount().numberNull().now();

		exception.expect(InsertAccountException.class);
		exception.expectMessage("The number must be informed");
		
		service.insertAccount(account);
	}

	
	@Test
	public void testInsertAccountIbanInvalid() {
		Account account = AccountBuilder.oneAccount().ibanInvalid().now();

		exception.expect(InsertAccountException.class);
		exception.expectMessage("The size of the iban should be 20");
		
		service.insertAccount(account);
	}
	
	@Test
	public void testTransferValueZero() {
		TransactionDTO dto = TransactionDTOBuilder.oneTransactionDTO().valueZero().now();

		exception.expect(TransactionRuleException.class);
		exception.expectMessage("The value must be greater than 0");
		
		service.transfer(dto);
	}
	
	@Test
	public void testTransferInsufficientBalance() {
		TransactionDTO dto = TransactionDTOBuilder.oneTransactionDTO().highValue().now();

		exception.expect(TransactionRuleException.class);
		exception.expectMessage("Account debit with insufficient balance");
		
		service.transfer(dto);
	}
	
	@Test
	public void testTransferIbanInvalid() {
		TransactionDTO dto = TransactionDTOBuilder.oneTransactionDTO().ibanDebitInvalid().now();

		exception.expect(AccountNotFoundException.class);
		exception.expectMessage("Invalid account iban " + dto.getIbanDebit());
		
		service.transfer(dto);
	}
}
