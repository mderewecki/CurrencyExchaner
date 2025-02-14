package com.nn.currencyexchanger.infrastructure.client.nbp

import com.nn.currencyexchanger.SpecificationIT
import org.springframework.beans.factory.annotation.Autowired

import static com.nn.currencyexchanger.domain.account.model.Currency.EUR
import static com.nn.currencyexchanger.domain.account.model.Currency.USD

class ExchangeRateServiceIT extends SpecificationIT {

    @Autowired
    ExchangeRateService exchangeRateService

    def "Should get exchange ask rate"() {
        expect:
        exchangeRateService.getExchangeAskRate(currency) == rate

        where:
        currency | rate
        USD      | BigDecimal.valueOf(4.0795)
        EUR      | BigDecimal.valueOf(4.2155)
    }

    def "Should get exchange bid rate"() {
        expect:
        exchangeRateService.getExchangeBidRate(currency) == rate

        where:
        currency | rate
        USD      | BigDecimal.valueOf(3.9987)
        EUR      | BigDecimal.valueOf(4.1321)
    }

}
