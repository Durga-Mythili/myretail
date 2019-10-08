package com.myretail.service

import com.myretail.config.AppConfig
import com.myretail.config.RedSkyConfig
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class RedskyClientSpec extends Specification {

    AppConfig appConfig
    RestTemplate restTemplate
    RedskyClient redskyClient

    void setup() {
        appConfig = new AppConfig(
                redsky: new RedSkyConfig(
                        url: 'http://localhost:8080',
                        query: '?exchange=abc'
                )
        )
        restTemplate = Mock()
        redskyClient = new RedskyClient(appConfig, restTemplate)
    }

    void 'I can return object from Redsky api call'() {
        given:
        int productId = 12345


    }

}
