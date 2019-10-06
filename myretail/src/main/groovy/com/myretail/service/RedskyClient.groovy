package com.myretail.service

import com.myretail.config.AppConfig
import com.myretail.redsky.RedSkyProduct
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class RedskyClient {

    AppConfig appConfig
    RestTemplate restTemplate

    RedskyClient(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig
        this.restTemplate = restTemplate
    }

    RedSkyProduct productDetails(int productId) {
        URI uri = URI.create("${appConfig.redsky.url}${productId}${appConfig.redsky.query}")
        println(uri)
        RedSkyProduct redSkyProduct
       try {
         redSkyProduct =  restTemplate.getForObject(uri, RedSkyProduct)
       } catch (HttpClientErrorException e) {
           // print errors
        redSkyProduct = null
       }
        return redSkyProduct
    }

}
