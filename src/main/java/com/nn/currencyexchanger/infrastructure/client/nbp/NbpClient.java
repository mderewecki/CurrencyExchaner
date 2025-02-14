package com.nn.currencyexchanger.infrastructure.client.nbp;

import com.nn.currencyexchanger.domain.account.model.Currency;
import com.nn.currencyexchanger.infrastructure.client.nbp.dto.NbpResponseDto;
import com.nn.currencyexchanger.infrastructure.client.nbp.exception.NoExchangeRateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
class NbpClient {

    private final RestClient nbpRestClient;

    NbpResponseDto getCurrentExchangeRate(Currency currency) {
        return nbpRestClient.get()
                .uri("/exchangerates/rates/c/" + currency.getCode() + "/today")
                .retrieve()
                .onStatus(
                        status -> status.isSameCodeAs(HttpStatus.NOT_FOUND),
                        (_, _) -> {
                            throw new NoExchangeRateException(currency.getCode());
                        })
                .toEntity(NbpResponseDto.class)
                .getBody();
    }

}
