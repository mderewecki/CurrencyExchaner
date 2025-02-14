package com.nn.currencyexchanger.infrastructure.controller;

import com.nn.currencyexchanger.domain.account.model.Account;
import com.nn.currencyexchanger.infrastructure.controller.dto.AccountBalancesDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface AccountDtoMapper {

    AccountBalancesDto toAccountDto(Account account);

}
