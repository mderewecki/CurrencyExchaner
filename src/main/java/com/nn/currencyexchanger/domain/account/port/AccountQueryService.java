package com.nn.currencyexchanger.domain.account.port;

import com.nn.currencyexchanger.domain.account.model.Account;

public interface AccountQueryService {

    Account getAccount(Long accountId);

}
