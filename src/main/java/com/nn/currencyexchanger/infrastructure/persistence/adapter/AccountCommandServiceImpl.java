package com.nn.currencyexchanger.infrastructure.persistence.adapter;

import com.nn.currencyexchanger.domain.account.model.Account;
import com.nn.currencyexchanger.domain.account.port.AccountCommandService;
import com.nn.currencyexchanger.infrastructure.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountCommandServiceImpl implements AccountCommandService {

    private final AccountRepository accountRepository;
    private final AccountEntityMapper accountEntityMapper;

    @Override
    public Long saveAccount(Account account) {
        return accountRepository.save(accountEntityMapper.toAccountEntity(account)).getId();
    }

}
