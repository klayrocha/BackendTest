package com.klayrocha.revolut.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.klayrocha.revolut.entity.Account;
import com.klayrocha.revolut.entity.Transaction;
import com.klayrocha.revolut.entity.TransactionDTO;
import com.klayrocha.revolut.entity.TypeTransaction;
import com.klayrocha.revolut.exception.AccountNotFoundException;
import com.klayrocha.revolut.exception.InsertAccountException;
import com.klayrocha.revolut.exception.TransactionRuleException;

/**
 * Class of service responsible for all operations related to account and money transfer
 * @author klayrocha
 *
 */
public class AccountService {

	private static final Logger logger = Logger.getLogger(AccountService.class.getName());
	
	private static List<Account> accounts =  new CopyOnWriteArrayList<Account>();

	/**
	 * Accounts created in memory to run the tests
	 */
	static {
		Account accountKlay = new Account(new BigInteger("3920012223230888"), "Klay Rocha", "PT233920012223230888","BCOMPTPL", new BigDecimal(500));
		accounts.add(accountKlay);

		Account accountNiko = new Account(new BigInteger("1999992223230790"), "Niko King", "PT111999992223230790","BIMOMZMX", new BigDecimal(1000));
		accounts.add(accountNiko);
	}

	/**
	 * Method responsible for including an account
	 * @param account
	 * @return account
	 */
	public void insertAccount(Account account) {
		validateAccount(account);
		accounts.add(account);
	}

	/**
	 * Method responsible for validating the required account fields
	 * @param account
	 */
	private void validateAccount(Account account) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();
	    Set<ConstraintViolation<Account>> errors = validator.validate(account);
	    if(errors.size() > 0) {
	    	StringBuffer sb = new StringBuffer();
	    	errors.forEach( c -> sb.append(" "+c.getMessage()));
	    	throw new InsertAccountException(sb.toString());
	    }
	}

	/**
	 * Method responsible for searching the account by the informed
	 * @param iban
	 * @return account
	 */
	public Account findByIban(String iban) {
		Optional<Account> accountOptional = accounts.stream().filter(c -> c.getIban().equals(iban)).findAny();
		if (accountOptional.isPresent()) {
			return accountOptional.get();
		} else {
			throw new AccountNotFoundException("Invalid account iban " + iban);
		}
	}

	/**
	 * Method responsible for transferring money
	 * @param transactionDTO
	 * @return status
	 */
    public void transfer(TransactionDTO transactionDTO) {
        logger.info("transfer - start [" + Thread.currentThread().getId() + "]");

        EntityLockWrapper<Account> accountDebitEntityLockWrapper = null;
        EntityLockWrapper<Account> accountCreditEntityLockWrapper = null;

        try {
            // Get debit account and try lock it
            Account accountDebit = findByIban(transactionDTO.getIbanDebit());
            accountDebitEntityLockWrapper = new EntityLockWrapper<>(accountDebit);
            accountDebitEntityLockWrapper.tryLock();
            logger.info("Account debit locked [" +  + Thread.currentThread().getId() + "]");

            // check debit account balance
            if (isBalanceNotSufficient(transactionDTO, accountDebit)) {
                throw new TransactionRuleException("Account debit with insufficient balance");
            }

            // get credit account and try lock it
            Account accountCredit = findByIban(transactionDTO.getIbanCredit());
            accountCreditEntityLockWrapper = new EntityLockWrapper<>(accountCredit);
            accountCreditEntityLockWrapper.tryLock();
            logger.info("Account credit locked [" + Thread.currentThread().getId() + "]");

			if (isTransactionValueNegative(transactionDTO)) {
				throw new TransactionRuleException("The value must be greater than 0");
			}

            // debit and credit accounts are locked, than is secure change them
            saveTransactions(accountCredit, accountDebit, transactionDTO.getValue(), transactionDTO.getDescription());
        } finally {
        	// unlock account objects
			unlockAccount(accountDebitEntityLockWrapper, "Account debit unlocked");
			unlockAccount(accountCreditEntityLockWrapper, "Account credit unlocked");

			logger.info("transfer - end [" + Thread.currentThread().getId() + "]");
        }
    }
    
	private void unlockAccount(EntityLockWrapper<Account> accountDebitEntityLockWrapper, String logMessage) {
		if (accountDebitEntityLockWrapper != null) {
			accountDebitEntityLockWrapper.unlock();
			logger.info(logMessage + "[" + Thread.currentThread().getId() + "]");
		}
	}

	private boolean isTransactionValueNegative(TransactionDTO transactionDTO) {
		return transactionDTO.getValue().intValue() <= 0;
	}

	private boolean isBalanceNotSufficient(TransactionDTO transactionDTO, Account account) {
		return account.getBalance().compareTo(transactionDTO.getValue()) < 0;
	}


	/**
	 * Method responsible for including account transactions and changing account balance
	 * @param account
	 * @return account
	 */
	private void saveTransactions(Account accountCredit, Account accountDebit, BigDecimal value, String description) {
		Transaction transactionDebit = new Transaction(accountDebit, value, TypeTransaction.Debit, new Date(),description);
		accountDebit.getTransactions().add(transactionDebit);
		accountDebit.setBalance(accountDebit.getBalance().subtract(value));
		logger.info("Account debid changed to " + accountDebit.getBalance() +  " [" + Thread.currentThread().getId() + "]");

		Transaction transactionCredit = new Transaction(accountCredit, value, TypeTransaction.Credit, new Date(),description);
		accountCredit.getTransactions().add(transactionCredit);
		accountCredit.setBalance(accountCredit.getBalance().add(value));
		logger.info("Account credit changed to " + accountCredit.getBalance() +  " [" + Thread.currentThread().getId() + "]");
	}
}