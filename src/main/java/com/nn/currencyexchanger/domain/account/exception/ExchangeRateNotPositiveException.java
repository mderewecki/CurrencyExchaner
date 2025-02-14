package com.nn.currencyexchanger.domain.account.exception;

public class ExchangeRateNotPositiveException extends ValidationException {
    public ExchangeRateNotPositiveException() {
        super("Exchange rate must be positive");
    }
}
