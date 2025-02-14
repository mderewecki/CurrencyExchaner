package com.nn.currencyexchanger.domain.account.exception;

public class InsufficientFundsException extends ValidationException {
    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}
