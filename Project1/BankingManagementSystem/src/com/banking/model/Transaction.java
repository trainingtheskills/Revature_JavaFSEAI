package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * One row of the transactions table.
 * For TRANSFER rows the amount is signed: negative = debit (sender), positive = credit (receiver).
 */
public class Transaction {

    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAWAL = "WITHDRAWAL";
    public static final String TRANSFER = "TRANSFER";

    private int transactionId;
    private long accountNumber;
    private String customerName;
    private String transactionType;
    private BigDecimal amount;
    private String transactionReference;
    private LocalDateTime transactionDate;

    public Transaction(long accountNumber, String transactionType, BigDecimal amount,
                       String transactionReference) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionReference = transactionReference;
        this.transactionDate = LocalDateTime.now();
    }

    public Transaction(int transactionId, long accountNumber, String customerName, String transactionType,
                       BigDecimal amount, String transactionReference, LocalDateTime transactionDate) {
        this(accountNumber, transactionType, amount, transactionReference);
        this.transactionId = transactionId;
        this.customerName = customerName;
        this.transactionDate = transactionDate;
    }

    /** Human-friendly label: splits TRANSFER into OUT / IN based on the sign. */
    public String getDisplayType() {
        if (TRANSFER.equals(transactionType)) {
            return amount.signum() < 0 ? "TRANSFER OUT" : "TRANSFER IN";
        }
        return transactionType;
    }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
}
