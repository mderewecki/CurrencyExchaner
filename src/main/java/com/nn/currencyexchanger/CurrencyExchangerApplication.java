package com.nn.currencyexchanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CurrencyExchangerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangerApplication.class, args);
    }

}
