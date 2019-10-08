package com.myretail

import com.myretail.config.AppConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableConfigurationProperties(AppConfig)
@EnableMongoRepositories(basePackages = "com.myretail.repository")
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
