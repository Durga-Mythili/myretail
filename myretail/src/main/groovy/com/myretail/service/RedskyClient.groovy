package com.myretail.service

import com.myretail.config.AppConfig
import com.myretail.redsky.RedSkyProduct
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.UnknownHttpStatusCodeException

@Slf4j
@Service
class RedskyClient {

    AppConfig appConfig
    RestTemplate restTemplate

    RedskyClient(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig
        this.restTemplate = restTemplate
    }

    /*
    * obtains product information from redsky by connecting with URl and passing productId
    * returns null if product is not found
    * */
    RedSkyProduct productDetails(int productId) {
        URI uri = URI.create("${appConfig.redsky.url}${productId}${appConfig.redsky.query}")
        RedSkyProduct redSkyProduct = null
       try {
         redSkyProduct =  restTemplate.getForObject(uri, RedSkyProduct)
       } catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
           log.error("Error while connecting with redsky product id ${productId}", e)
       }
        return redSkyProduct
    }

}
