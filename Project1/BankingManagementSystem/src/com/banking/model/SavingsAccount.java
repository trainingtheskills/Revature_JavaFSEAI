package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsAccount extends Account {

    public SavingsAccount(long accountNumber, int customerId, BigDecimal balance, LocalDate createdDate) {
        super(accountNumber, customerId, balance, createdDate);
    }

    @Override
    public String getAccountType() {
        return SAVINGS;
    }

    /** Savings accounts may not go below zero. Change here to add a minimum-balance rule. */
    @Override
    public BigDecimal getMinimumBalance() {
        return BigDecimal.ZERO;
    }
}
