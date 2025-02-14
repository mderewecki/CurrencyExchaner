package com.nn.currencyexchanger.domain.account.port;

import com.nn.currencyexchanger.domain.account.model.Account;
import com.nn.currencyexchanger.domain.account.model.Currency;

import java.math.BigDecimal;

public interface AccountPort {

    long createAccount(Account account);

    void exchangeFromPln(Long accountId, BigDecimal amount, Currency currency);

    void exchangeToPln(Long accountId, BigDecimal amount, Currency currency);

    Account getAccount(Long accountId);

}
