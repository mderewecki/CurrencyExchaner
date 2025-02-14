package com.nn.currencyexchanger.infrastructure.client.nbp.dto;

import java.util.Set;

public record NbpResponseDto(
        String code,
        Set<RateDto> rates
) {
}
