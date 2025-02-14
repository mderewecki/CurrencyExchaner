package com.nn.currencyexchanger.domain.account.exception;

import com.nn.currencyexchanger.domain.account.model.Currency;

public class NoBalanceForCurrencyException extends NotFoundException {
    public NoBalanceForCurrencyException(Currency currency) {
        super(String.format("No balance available for currency %s", currency.getCode()));
    }
}
