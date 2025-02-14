package com.nn.currencyexchanger.domain.account.model;

import com.nn.currencyexchanger.domain.account.exception.CannotExchangeToTheSameCurrency;
import com.nn.currencyexchanger.domain.account.exception.ExchangeRateNotPositiveException;
import com.nn.currencyexchanger.domain.account.exception.InsufficientFundsException;
import com.nn.currencyexchanger.domain.account.exception.NoBalanceForCurrencyException;
import com.nn.currencyexchanger.domain.account.exception.InitialBalanceCannotBeNegativeException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nn.currencyexchanger.domain.account.model.Currency.PLN;

@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Account {

    @Getter
    private Long id;

    @Getter
    private final String name;

    @Getter
    private final String surname;

    private Set<Balance> balances = new HashSet<>();

    public static Account createAccount(String name, String surname, @NonNull BigDecimal balanceInPln) {
        log.info("Creating account for user {} {} with initial pln balance {}", name, surname, balanceInPln);

        if (BigDecimal.ZERO.compareTo(balanceInPln) > 0) {
            throw new InitialBalanceCannotBeNegativeException();
        }

        Account newAccount = new Account(name, surname);

        newAccount.initializeBalances();
        newAccount.getBalance(PLN).add(balanceInPln);

        return newAccount;
    }

    public Set<Balance> getBalances() {
        return Set.copyOf(balances);
    }

    public void exchangeFromPln(@NonNull BigDecimal amountInPln, @NonNull Currency currency, @NonNull BigDecimal exchangeRate) {
        log.info("Exchanging currency {} {} to {} for account with id {} with rate {}", amountInPln, PLN, currency, this.id, exchangeRate);

        validateExchange(amountInPln, PLN, currency, exchangeRate);

        getBalance(PLN).subtract(amountInPln);
        getBalance(currency).add(
                amountInPln.divide(
                        exchangeRate,
                        2,
                        RoundingMode.HALF_UP
                )
        );

        log.info("Balances after exchange: {} {} and {} {}", getBalance(PLN).getBalance(), PLN, getBalance(currency).getBalance(), currency);
    }

    public void exchangeToPln(@NonNull BigDecimal amount, @NonNull Currency currency, @NonNull BigDecimal exchangeRate) {
        log.info("Exchanging currency {} {} to {} for account with id {} with rate {}", amount, currency, PLN, this.id, exchangeRate);
        validateExchange(amount, currency, PLN, exchangeRate);

        getBalance(currency).subtract(amount);
        getBalance(PLN).add(
                amount.multiply(exchangeRate)
                        .setScale(2, RoundingMode.HALF_UP)
        );

        log.info("Balances after exchange: {} {} and {} {}", getBalance(PLN).getBalance(), PLN, getBalance(currency).getBalance(), currency);
    }

    Balance getBalance(@NonNull Currency currency) {
        return this.balances.stream()
                .filter(balance -> balance.getCurrency().equals(currency))
                .findAny()
                .orElseThrow(() -> new NoBalanceForCurrencyException(currency));
    }

    private void initializeBalances() {
        List.of(Currency.values())
                .forEach(currency -> balances.add(new Balance(currency)));
    }

    private void validateExchange(BigDecimal amount, Currency from, Currency to, BigDecimal exchangeRate) {
        validateExchangeRate(exchangeRate);
        validateCurrencies(from, to);
        validateAmount(amount, from);
    }

    private void validateExchangeRate(BigDecimal exchangeRate) {
        if (BigDecimal.ZERO.compareTo(exchangeRate) >= 0) {
            throw new ExchangeRateNotPositiveException();
        }
    }

    private void validateCurrencies(Currency from, Currency to) {
        if (from.equals(to)) {
            throw new CannotExchangeToTheSameCurrency();
        }
    }

    private void validateAmount(BigDecimal amount, Currency from) {
        if (getBalance(from).getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
    }

}
