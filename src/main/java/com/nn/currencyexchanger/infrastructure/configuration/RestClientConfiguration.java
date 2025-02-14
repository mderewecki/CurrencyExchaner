package com.nn.currencyexchanger.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfiguration {

    private final RestClientProperties restClientProperties;

    @Bean
    RestClient nbpRestClient() {
        return RestClient.create(restClientProperties.getNbpBaseUrl());
    }

}
