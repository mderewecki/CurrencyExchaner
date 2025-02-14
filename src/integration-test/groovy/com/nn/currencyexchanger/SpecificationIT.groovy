package com.nn.currencyexchanger

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@AutoConfigureWireMock(port = 0, stubs="classpath:/stubs")
@ActiveProfiles("test")
class SpecificationIT extends Specification {

    @Autowired
    protected WireMockServer wireMockServer

}
