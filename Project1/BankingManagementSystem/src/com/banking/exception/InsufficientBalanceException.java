package com.banking.exception;

/** Thrown when a withdrawal or transfer would exceed the available balance. */
public class InsufficientBalanceException extends Exception {

    private static final long serialVersionUID = 1L;

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
