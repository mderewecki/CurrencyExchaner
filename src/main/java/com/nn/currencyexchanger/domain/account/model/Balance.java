package com.nn.currencyexchanger.domain.account.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Balance {

    private Long id;
    private BigDecimal balance = BigDecimal.ZERO;
    private final Currency currency;

    void add(BigDecimal amount) {
        log.info("Adding {} {}", amount, currency);
        balance = balance.add(amount);
    }

    void subtract(BigDecimal amount) {
        log.info("Subtracting {} {}", amount, currency);
        balance = balance.subtract(amount);
    }

}
