package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrentAccount extends Account {

    public CurrentAccount(long accountNumber, int customerId, BigDecimal balance, LocalDate createdDate) {
        super(accountNumber, customerId, balance, createdDate);
    }

    @Override
    public String getAccountType() {
        return CURRENT;
    }

    /** No overdraft in this version. Return a negative value here to allow one. */
    @Override
    public BigDecimal getMinimumBalance() {
        return BigDecimal.ZERO;
    }
}
