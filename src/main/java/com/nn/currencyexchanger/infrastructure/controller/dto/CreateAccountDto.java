package com.nn.currencyexchanger.infrastructure.controller.dto;

import java.math.BigDecimal;

public record CreateAccountDto(
        String name,
        String surname,
        BigDecimal balanceInPln
) {
}
