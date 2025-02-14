package com.nn.currencyexchanger.domain.account.model

import com.nn.currencyexchanger.domain.account.exception.CannotExchangeToTheSameCurrency
import com.nn.currencyexchanger.domain.account.exception.ExchangeRateNotPositiveException
import com.nn.currencyexchanger.domain.account.exception.InsufficientFundsException
import com.nn.currencyexchanger.domain.account.exception.InitialBalanceCannotBeNegativeException
import spock.lang.Specification

import static com.nn.currencyexchanger.domain.account.model.Currency.EUR
import static com.nn.currencyexchanger.domain.account.model.Currency.PLN
import static com.nn.currencyexchanger.domain.account.model.Currency.USD

class AccountSpec extends Specification {

    def "Should create new account with initialized balance in pln"() {
        given:
        def name = "Michal"
        def surname = "Derewecki"
        def balanceInPln = BigDecimal.valueOf(initialBalance)

        when:
        def createdAccount = Account.createAccount(name, surname, balanceInPln)

        then:
        createdAccount.name == name
        createdAccount.surname == surname
        createdAccount.getBalance(PLN).balance == balanceInPln
        createdAccount.getBalance(USD).balance == BigDecimal.ZERO

        where:
        initialBalance << [0, 100]
    }

    def "Should throw exception when initial balance is negative"() {
        when:
        Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(-0.01))

        then:
        thrown InitialBalanceCannotBeNegativeException
    }

    def "Should exchange pln to usd"() {
        given:
        def account = Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(100))

        when:
        account.exchangeFromPln(amount, USD, rate)

        then:
        account.getBalance(PLN).balance == expectedPlnBalance
        account.getBalance(USD).balance == expectedUsdBalance

        where:
        amount                  | rate                    | expectedPlnBalance      | expectedUsdBalance
        BigDecimal.valueOf(35)  | BigDecimal.valueOf(3.5) | BigDecimal.valueOf(65)  | BigDecimal.valueOf(10)
        BigDecimal.valueOf(0)   | BigDecimal.valueOf(3.5) | BigDecimal.valueOf(100) | BigDecimal.valueOf(0)
        BigDecimal.valueOf(100) | BigDecimal.valueOf(3.5) | BigDecimal.valueOf(0)   | BigDecimal.valueOf(28.57)
    }

    def "Should throw exception when amount greater than balance in pln"() {
        given:
        def account = Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(100))

        when:
        account.exchangeFromPln(BigDecimal.valueOf(100.01), USD, BigDecimal.valueOf(4))

        then:
        thrown InsufficientFundsException
    }

    def "Should throw exception if exchange rate is not positive while exchanging from pln"() {
        given:
        def account = Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(100))

        when:
        account.exchangeFromPln(BigDecimal.valueOf(10), USD, rate)

        then:
        thrown ExchangeRateNotPositiveException

        where:
        rate << [BigDecimal.valueOf(0), BigDecimal.valueOf(-1)]
    }

    def "Should throw exception while trying to exchange from pln to pln using exchangeFromPln"() {
        given:
        def account = Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(100))

        when:
        account.exchangeFromPln(BigDecimal.valueOf(10), PLN, BigDecimal.valueOf(4))

        then:
        thrown CannotExchangeToTheSameCurrency
    }

    def "Should exchange usd to pln"() {
        given:
        def account = Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(0))
        account.getBalance(USD).add(BigDecimal.valueOf(100))

        when:
        account.exchangeToPln(amount, USD, rate)

        then:
        account.getBalance(PLN).balance == expectedPlnBalance
        account.getBalance(USD).balance == expectedUsdBalance

        where:
        amount                  | rate                    | expectedUsdBalance      | expectedPlnBalance
        BigDecimal.valueOf(35)  | BigDecimal.valueOf(3.5) | BigDecimal.valueOf(65)  | BigDecimal.valueOf(122.50)
        BigDecimal.valueOf(0)   | BigDecimal.valueOf(3.5) | BigDecimal.valueOf(100) | BigDecimal.valueOf(0)
        BigDecimal.valueOf(100) | BigDecimal.valueOf(3.5) | BigDecimal.valueOf(0)   | BigDecimal.valueOf(350)
    }

    def "Should throw exception when amount greater than balance in usd"() {
        given:
        def account = Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(0))
        account.getBalance(USD).add(BigDecimal.valueOf(100))

        when:
        account.exchangeToPln(BigDecimal.valueOf(100.01), USD, BigDecimal.valueOf(4))

        then:
        thrown InsufficientFundsException
    }

    def "Should throw exception if exchange rate is not positive while exchanging to pln"() {
        given:
        def account = Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(0))
        account.getBalance(USD).add(BigDecimal.valueOf(100))

        when:
        account.exchangeToPln(BigDecimal.valueOf(10), USD, rate)

        then:
        thrown ExchangeRateNotPositiveException

        where:
        rate << [BigDecimal.valueOf(0), BigDecimal.valueOf(-1)]
    }

    def "Should throw exception while trying to exchange from pln to pln using exchangeToPln"() {
        given:
        def account = Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(0))
        account.getBalance(USD).add(BigDecimal.valueOf(100))

        when:
        account.exchangeToPln(BigDecimal.valueOf(10), PLN, BigDecimal.valueOf(4))

        then:
        thrown CannotExchangeToTheSameCurrency
    }

    def "Should get balance"() {
        given:
        def account = Account.createAccount("Michal", "Derewecki", BigDecimal.valueOf(100))

        when:
        def balance = account.getBalance(currency)

        then:
        balance.balance == expectedBalance
        balance.currency == currency

        where:
        currency | expectedBalance
        PLN      | BigDecimal.valueOf(100)
        USD      | BigDecimal.valueOf(0)
        EUR      | BigDecimal.valueOf(0)
    }

}
