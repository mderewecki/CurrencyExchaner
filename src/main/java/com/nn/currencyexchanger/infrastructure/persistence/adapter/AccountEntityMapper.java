package com.nn.currencyexchanger.infrastructure.persistence.adapter;

import com.nn.currencyexchanger.domain.account.model.Account;
import com.nn.currencyexchanger.infrastructure.persistence.entity.AccountEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface AccountEntityMapper {

    AccountEntity toAccountEntity(Account account);

    Account toAccountDomain(AccountEntity accountEntity);

    @AfterMapping
    default void afterMappingToEntity(@MappingTarget AccountEntity accountEntity) {
        accountEntity.getBalances().forEach(
                balance -> balance.setAccount(accountEntity)
        );
    }

}
