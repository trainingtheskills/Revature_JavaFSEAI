package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Base class for all bank accounts (table: accounts).
 * Subclasses supply account-specific rules (polymorphism / overriding).
 */
public abstract class Account {

    public static final String SAVINGS = "SAVINGS";
    public static final String CURRENT = "CURRENT";

    private long accountNumber;
    private int customerId;
    private BigDecimal balance;
    private LocalDate createdDate;

    protected Account(long accountNumber, int customerId, BigDecimal balance, LocalDate createdDate) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
        this.createdDate = createdDate;
    }

    /** Account type label stored in the database (SAVINGS / CURRENT). */
    public abstract String getAccountType();

    /** Lowest balance this account type may hold after a withdrawal. */
    public abstract BigDecimal getMinimumBalance();

    /** Common rule, driven by the account-specific minimum balance. */
    public boolean canWithdraw(BigDecimal amount) {
        return balance.subtract(amount).compareTo(getMinimumBalance()) >= 0;
    }

    /** Factory: builds the right subclass for the type read from the database. */
    public static Account of(String type, long accountNumber, int customerId,
                             BigDecimal balance, LocalDate createdDate) {
        if (CURRENT.equalsIgnoreCase(type)) {
            return new CurrentAccount(accountNumber, customerId, balance, createdDate);
        }
        return new SavingsAccount(accountNumber, customerId, balance, createdDate);
    }

    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    @Override
    public String toString() {
        return String.format("Account No: %d | Type: %-7s | Balance: %12s | Opened: %s",
                accountNumber, getAccountType(), balance.toPlainString(), createdDate);
    }
}
