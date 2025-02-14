package com.nn.currencyexchanger.infrastructure.controller

import com.nn.currencyexchanger.ApiIT
import com.nn.currencyexchanger.infrastructure.controller.dto.CreateAccountDto
import com.nn.currencyexchanger.infrastructure.controller.dto.ExchangeDto

import static com.nn.currencyexchanger.domain.account.model.Currency.USD
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.matchesPattern
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccountApiIT extends ApiIT {

    def "Should create account"() {
        given:
        def createAccountDto = prepareCreateAccountDto(initialBalance)

        when:
        def perform = mockMvc.perform(post("/account")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAccountDto)))

        then:
        perform.andExpectAll(
                status().isOk(),
                content().string(matchesPattern("[0-9]*"))
        )

        where:
        initialBalance << [BigDecimal.TEN, BigDecimal.ZERO]
    }

    def "Should return bad request when initial balance less than 0"() {
        given:
        def createAccountDto = prepareCreateAccountDto(-0.01)

        when:
        def perform = mockMvc.perform(post("/account")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAccountDto)))

        then:
        perform.andExpect(
                status().isBadRequest()
        )
    }

    def "Should return account information"() {
        given:
        def accountId = createAccount()

        when:
        def perform = mockMvc.perform(get("/account/${accountId}")
                .contentType(APPLICATION_JSON))

        then:
        perform.andExpectAll(
                status().isOk(),
                jsonPath('$.name', is("Michal")),
                jsonPath('$.surname', is("Derewecki")),
                jsonPath('$.balances', hasSize(3)),
        )
    }

    def "Should return not found when account does not exist"() {
        when:
        def perform = mockMvc.perform(get("/account/1")
                .contentType(APPLICATION_JSON))

        then:
        perform.andExpect(status().isNotFound())
    }

    def "Should exchange from pln to usd"() {
        given:
        def accountId = createAccount()
        def exchangeDto = new ExchangeDto(BigDecimal.valueOf(10), USD)

        when:
        def perform = mockMvc.perform(post("/account/${accountId}/exchange-from-pln")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exchangeDto)))

        then:
        perform.andExpect(status().isOk())
    }

    def "Should return bad request when insufficient funds"() {
        given:
        def accountId = createAccount(BigDecimal.valueOf(10))
        def exchangeDto = new ExchangeDto(BigDecimal.valueOf(10.01), USD)

        when:
        def perform = mockMvc.perform(post("/account/${accountId}/exchange-from-pln")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exchangeDto)))

        then:
        perform.andExpect(status().isBadRequest())
    }

    def prepareCreateAccountDto(BigDecimal initialBalance = 1000) {
        return new CreateAccountDto(
                "Michal",
                "Derewecki",
                BigDecimal.valueOf(initialBalance)
        )
    }

    def createAccount(BigDecimal initialBalance = 1000) {
        return mockMvc.perform(post("/account")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prepareCreateAccountDto(initialBalance))))
                .andReturn()
                .getResponse()
                .getContentAsString()
    }

}
