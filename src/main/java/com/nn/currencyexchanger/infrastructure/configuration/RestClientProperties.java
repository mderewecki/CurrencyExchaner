package com.nn.currencyexchanger.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest-client")
@Setter
@Getter
public class RestClientProperties {
    private String nbpBaseUrl;
}
