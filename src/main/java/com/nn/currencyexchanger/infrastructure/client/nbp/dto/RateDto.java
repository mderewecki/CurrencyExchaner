package com.nn.currencyexchanger.infrastructure.client.nbp.dto;

import java.math.BigDecimal;

public record RateDto(
        BigDecimal bid,
        BigDecimal ask
) {
}
