package com.nn.currencyexchanger.infrastructure.client.nbp

import com.nn.currencyexchanger.SpecificationIT
import com.nn.currencyexchanger.infrastructure.client.nbp.exception.NoExchangeRateException
import org.springframework.beans.factory.annotation.Autowired

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.nn.currencyexchanger.domain.account.model.Currency.EUR
import static com.nn.currencyexchanger.domain.account.model.Currency.USD
import static org.springframework.http.HttpStatus.NOT_FOUND

class NbpClientIT extends SpecificationIT {

    @Autowired
    NbpClient nbpClient

    def "Should get current exchange rate"() {
        when:
        def result = nbpClient.getCurrentExchangeRate(currency)

        then:
        result.code() == expectedCode
        !result.rates().isEmpty()

        and:
        wireMockServer.verify(1, getRequestedFor(urlEqualTo("/nbp/api/exchangerates/rates/c/${currency.getCode()}/today")))

        where:
        currency | expectedCode
        USD      | "USD"
        EUR      | "EUR"
    }

    def "Should throw exception if no exchange rate"() {
        given:
        wireMockServer.stubFor(get(urlEqualTo("/nbp/api/exchangerates/rates/c/usd/today"))
                .willReturn(aResponse().withStatus(NOT_FOUND.value())))

        when:
        nbpClient.getCurrentExchangeRate(USD)

        then:
        thrown NoExchangeRateException
    }

}
