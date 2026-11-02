package com.banking.exception;

/** Thrown when an account number does not exist. */
public class InvalidAccountException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidAccountException(String message) {
        super(message);
    }
}
