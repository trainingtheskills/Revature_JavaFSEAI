package com.banking;

import com.banking.exception.InsufficientBalanceException;
import com.banking.exception.InvalidAccountException;
import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.model.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/** The banking operations supported by the application (abstraction). */
public interface BankOperations {

    Customer createCustomer(String name, String email, String phone) throws SQLException;

    Customer getCustomer(int customerId) throws SQLException;

    Account createAccount(int customerId, String accountType) throws SQLException;

    List<Account> getAccountsByCustomer(int customerId) throws SQLException;

    /** Search by account number. */
    Account getAccount(long accountNumber) throws InvalidAccountException, SQLException;

    void deposit(long accountNumber, BigDecimal amount) throws InvalidAccountException, SQLException;

    /** Overloaded convenience version. */
    void deposit(long accountNumber, double amount) throws InvalidAccountException, SQLException;

    void withdraw(long accountNumber, BigDecimal amount)
            throws InvalidAccountException, InsufficientBalanceException, SQLException;

    BigDecimal getBalance(long accountNumber) throws InvalidAccountException, SQLException;

    /** @return the shared transaction reference of the two transfer rows */
    String transfer(long fromAccount, long toAccount, BigDecimal amount)
            throws InvalidAccountException, InsufficientBalanceException, SQLException;

    List<Transaction> getTransactionHistory(long accountNumber) throws InvalidAccountException, SQLException;
}
