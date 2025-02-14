package com.nn.currencyexchanger.infrastructure.controller;

import com.nn.currencyexchanger.domain.account.exception.NotFoundException;
import com.nn.currencyexchanger.domain.account.exception.ValidationException;
import com.nn.currencyexchanger.domain.account.model.Account;
import com.nn.currencyexchanger.domain.account.port.AccountPort;
import com.nn.currencyexchanger.infrastructure.client.nbp.exception.NoExchangeRateException;
import com.nn.currencyexchanger.infrastructure.controller.dto.AccountBalancesDto;
import com.nn.currencyexchanger.infrastructure.controller.dto.CreateAccountDto;
import com.nn.currencyexchanger.infrastructure.controller.dto.ExchangeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountPort accountPort;
    private final AccountDtoMapper accountDtoMapper;

    @GetMapping("/{accountId}")
    public AccountBalancesDto getAccount(@PathVariable Long accountId) {
        try {
            return accountDtoMapper.toAccountDto(
                    accountPort.getAccount(accountId)
            );
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    @PostMapping
    public Long createAccount(@RequestBody CreateAccountDto createAccountDto) {
        try {
            return accountPort.createAccount(
                    Account.createAccount(
                            createAccountDto.name(),
                            createAccountDto.surname(),
                            createAccountDto.balanceInPln()
                    )
            );
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        } catch (ValidationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
    }

    @PostMapping("/{accountId}/exchange-from-pln")
    public void exchangeFromPln(@PathVariable Long accountId, @RequestBody ExchangeDto exchangeDto) {
        try {
            accountPort.exchangeFromPln(
                    accountId,
                    exchangeDto.amount(),
                    exchangeDto.currency()
            );
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        } catch (ValidationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage());
        } catch (NoExchangeRateException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    @PostMapping("/{accountId}/exchange-to-pln")
    public void exchangeToPln(@PathVariable Long accountId, @RequestBody ExchangeDto exchangeDto) {
        try {
            accountPort.exchangeToPln(
                    accountId,
                    exchangeDto.amount(),
                    exchangeDto.currency()
            );
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        } catch (ValidationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage());
        } catch (NoExchangeRateException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

}
