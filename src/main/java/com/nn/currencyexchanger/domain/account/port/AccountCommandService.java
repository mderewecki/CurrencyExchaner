package com.nn.currencyexchanger.domain.account.port;

import com.nn.currencyexchanger.domain.account.model.Account;

public interface AccountCommandService {

    Long saveAccount(Account account);

}
