package com.nn.currencyexchanger.domain.account.exception;

public class CannotExchangeToTheSameCurrency extends ValidationException {
    public CannotExchangeToTheSameCurrency() {
        super("Can't exchange currency to the same currency");
    }
}
