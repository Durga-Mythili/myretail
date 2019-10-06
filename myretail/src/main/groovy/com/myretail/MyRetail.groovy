package com.myretail

import com.myretail.config.AppConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableConfigurationProperties(AppConfig)
@Configuration
class MyRetail {
    static void main(String[] args) {
        SpringApplication.run(MyRetail)
    }
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate()
    }
}
