package com.myretail.service

import com.myretail.config.AppConfig
import com.myretail.config.RedSkyConfig
import com.myretail.redsky.Item
import com.myretail.redsky.Product
import com.myretail.redsky.ProductAvailability
import com.myretail.redsky.ProductDescription
import com.myretail.redsky.RedSkyProduct
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class RedskyClientSpec extends Specification {

    AppConfig appConfig
    RestTemplate restTemplate
    RedskyClient redskyClient
    RedSkyProduct redSkyProduct

    void setup() {
        appConfig = new AppConfig(
                redsky: new RedSkyConfig(
                        url: 'http://localhost:8080',
                        query: '?exchange=abc'
                )
        )
        restTemplate = Mock()
        redskyClient = new RedskyClient(appConfig, restTemplate)

        redSkyProduct = new RedSkyProduct(
                product: new Product(
                        productAvailability: new ProductAvailability(
                                id: '12345'
                        ),
                        item: new Item(
                                description: new ProductDescription(
                                        title: 'pink piggy bank girls toy'
                                )
                        )

                ))
    }

    void 'I can return object from Redsky api call'() {
        given:
        int productId = 12345

        when:
        RedSkyProduct product = redskyClient.productDetails(productId)

        then:
        1 * restTemplate.getForObject(_ as URI, RedSkyProduct) >> redSkyProduct
        product
        product.product.item.description == redSkyProduct.product.item.description
    }

    void 'I return null if error is obtained from Redsky api call'() {
        given:
        int productId = 12345

        when:
        RedSkyProduct product = redskyClient.productDetails(productId)

        then:
        1 * restTemplate.getForObject(_ as URI, RedSkyProduct) >> {throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)}
        !product
    }
}
