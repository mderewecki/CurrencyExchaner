package com.nn.currencyexchanger.infrastructure.controller.dto;

import com.nn.currencyexchanger.domain.account.model.Currency;

import java.math.BigDecimal;

public record BalanceDto(
        BigDecimal balance,
        Currency currency
) {
}
