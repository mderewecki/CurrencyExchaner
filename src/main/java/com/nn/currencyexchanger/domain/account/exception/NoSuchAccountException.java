package com.nn.currencyexchanger.domain.account.exception;

public class NoSuchAccountException extends NotFoundException {
    public NoSuchAccountException(Long accountId) {
        super(String.format("No account with id %d", accountId));
    }
}
