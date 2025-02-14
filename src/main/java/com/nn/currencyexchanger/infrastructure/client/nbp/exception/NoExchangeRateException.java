package com.nn.currencyexchanger.infrastructure.client.nbp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoExchangeRateException extends RuntimeException {
    public NoExchangeRateException(String currencyCode) {
        super(String.format("No exchange rate for currency code %s", currencyCode));
        log.error(this.getMessage());
    }
}
