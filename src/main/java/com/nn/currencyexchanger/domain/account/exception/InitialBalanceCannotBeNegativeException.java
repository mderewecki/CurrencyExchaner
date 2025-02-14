package com.nn.currencyexchanger.domain.account.exception;

public class InitialBalanceCannotBeNegativeException extends ValidationException {
    public InitialBalanceCannotBeNegativeException() {
        super("Initial balance cannot be negative");
    }
}
