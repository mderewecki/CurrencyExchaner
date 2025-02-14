package com.nn.currencyexchanger.infrastructure.client.nbp;

import com.nn.currencyexchanger.domain.account.model.Currency;
import com.nn.currencyexchanger.domain.account.port.ExchangeRatePort;
import com.nn.currencyexchanger.infrastructure.client.nbp.dto.RateDto;
import com.nn.currencyexchanger.infrastructure.client.nbp.exception.NoExchangeRateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeRateService implements ExchangeRatePort {

    private final NbpClient nbpClient;

    @Override
    public BigDecimal getExchangeAskRate(Currency currency) {
        return getRateDto(currency).ask();
    }

    @Override
    public BigDecimal getExchangeBidRate(Currency currency) {
        return getRateDto(currency).bid();
    }

    private RateDto getRateDto(Currency currency) {
        return nbpClient.getCurrentExchangeRate(currency)
                .rates()
                .stream()
                .findAny()
                .orElseThrow(() -> new NoExchangeRateException(currency.getCode()));
    }
}
