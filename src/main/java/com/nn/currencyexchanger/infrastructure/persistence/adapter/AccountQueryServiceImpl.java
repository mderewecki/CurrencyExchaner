package com.nn.currencyexchanger.infrastructure.persistence.adapter;

import com.nn.currencyexchanger.domain.account.exception.NoSuchAccountException;
import com.nn.currencyexchanger.domain.account.model.Account;
import com.nn.currencyexchanger.domain.account.port.AccountQueryService;
import com.nn.currencyexchanger.infrastructure.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountQueryServiceImpl implements AccountQueryService {

    private final AccountRepository accountRepository;
    private final AccountEntityMapper accountEntityMapper;

    @Override
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .map(accountEntityMapper::toAccountDomain)
                .orElseThrow(() -> new NoSuchAccountException(accountId));
    }
}
