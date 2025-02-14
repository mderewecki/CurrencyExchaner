package com.nn.currencyexchanger.domain.account.service;

import com.nn.currencyexchanger.domain.account.model.Account;
import com.nn.currencyexchanger.domain.account.model.Currency;
import com.nn.currencyexchanger.domain.account.port.AccountCommandService;
import com.nn.currencyexchanger.domain.account.port.AccountPort;
import com.nn.currencyexchanger.domain.account.port.AccountQueryService;
import com.nn.currencyexchanger.domain.account.port.ExchangeRatePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements AccountPort {

    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;
    private final ExchangeRatePort exchangeRatePort;

    @Override
    @Transactional
    public long createAccount(Account account) {
        Long accountId = accountCommandService.saveAccount(account);
        log.info("Account created with id {}", accountId);
        return accountId;
    }

    @Override
    @Transactional
    public void exchangeFromPln(Long accountId, BigDecimal amount, Currency currency) {
        Account account = accountQueryService.getAccount(accountId);

        account.exchangeFromPln(
                amount,
                currency,
                exchangeRatePort.getExchangeAskRate(currency)
        );

        accountCommandService.saveAccount(account);
    }

    @Override
    @Transactional
    public void exchangeToPln(Long accountId, BigDecimal amount, Currency currency) {
        Account account = accountQueryService.getAccount(accountId);

        account.exchangeToPln(
                amount,
                currency,
                exchangeRatePort.getExchangeBidRate(currency)
        );

        accountCommandService.saveAccount(account);
    }

    @Override
    public Account getAccount(Long accountId) {
        log.info("Get account with id {}", accountId);
        return accountQueryService.getAccount(accountId);
    }

}
