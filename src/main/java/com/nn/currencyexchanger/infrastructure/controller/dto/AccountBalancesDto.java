package com.nn.currencyexchanger.infrastructure.controller.dto;

import java.util.Set;

public record AccountBalancesDto(
        String name,
        String surname,
        Set<BalanceDto> balances
) {
}
