package com.nn.currencyexchanger.domain.account.port;

import com.nn.currencyexchanger.domain.account.model.Currency;

import java.math.BigDecimal;

public interface ExchangeRatePort {

    BigDecimal getExchangeAskRate(Currency currency);

    BigDecimal getExchangeBidRate(Currency currency);

}
