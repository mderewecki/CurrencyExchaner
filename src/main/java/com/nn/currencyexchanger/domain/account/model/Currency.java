package com.nn.currencyexchanger.domain.account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Currency {

    PLN("pln"),
    USD("usd"),
    EUR("eur");

    private final String code;

}
