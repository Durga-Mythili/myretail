package com.myretail.controllers

import com.myretail.Price
import com.myretail.ProductPrice
import com.myretail.exceptions.ConflictException
import com.myretail.exceptions.ContentNotModifiedException
import com.myretail.exceptions.ProductNotFoundException
import com.myretail.service.MyRetailService
import com.myretail.util.CurrencyCode
import spock.lang.Specification

class MyRetailControllerSpec extends Specification {
    MyRetailController myRetailController
    MyRetailService service
    ProductPrice productPrice

    void setup() {
        service = Mock()
        myRetailController = new MyRetailController(service)
       productPrice = new ProductPrice(
                id: 12345,
                name: 'pink piggy bank girls toy',
                currentPrice:  new Price(
                        value: 30.0,
                        currencyCode: CurrencyCode.USD
                )
        )

    }

    void 'I can get product price information'() {
        given:
        int productId = 12345
        when:
        ProductPrice price = myRetailController.getProduct(productId)
        then:
        1 * service.getProductPrice(productId) >> productPrice
        price.id == productPrice.id
        price.name == productPrice.name
        price.currentPrice.value == productPrice.currentPrice.value
        price.currentPrice.currencyCode == productPrice.currentPrice.currencyCode
    }

    void 'I cannot get product price information'() {
        given:
        int productId = 12345
        when:
        myRetailController.getProduct(productId)
        then:
        1 * service.getProductPrice(productId) >> null
        thrown(ProductNotFoundException)

    }

    void 'I can update product price information'() {
        given:
        int productId = 12345
        ProductPrice updateProductPrice = new ProductPrice(
                id: 12345,
                name: 'pink piggy bank girls toy',
                currentPrice:  new Price(
                        value: 30.0,
                        currencyCode: CurrencyCode.USD
                )
        )

        when:
        ProductPrice price = myRetailController.updateProductPrice(productId, updateProductPrice)
        then:
        1 * service.updateProductPrice(productId, updateProductPrice) >> updateProductPrice
        price.id == updateProductPrice.id
        price.name == updateProductPrice.name
        price.currentPrice.value == updateProductPrice.currentPrice.value
        price.currentPrice.currencyCode == updateProductPrice.currentPrice.currencyCode
    }

    void 'I cannot update product price information'() {
        given:
        int productId = 12345
        ProductPrice updateProductPrice = new ProductPrice(
                id: 12345,
                name: 'pink piggy bank girls toy',
                currentPrice:  new Price(
                        value: 0.0,
                        currencyCode: CurrencyCode.USD
                )
        )

        when:
        myRetailController.updateProductPrice(productId, updateProductPrice)
        then:
        0 * service.updateProductPrice(productId, updateProductPrice) >> updateProductPrice
        thrown(ConflictException)
    }

    void 'I cannot update product price information with mismatched product id'() {
        given:
        int productId = 12346
        ProductPrice updateProductPrice = new ProductPrice(
                id: 12345,
                name: 'pink piggy bank girls toy',
                currentPrice:  new Price(
                        value: 20.0,
                        currencyCode: CurrencyCode.USD
                )
        )

        when:
        myRetailController.updateProductPrice(productId, updateProductPrice)
        then:
        0 * service.updateProductPrice(productId, updateProductPrice) >> updateProductPrice
        thrown(ConflictException)
    }

    void 'I return error if unable to update price'() {
        given:
        int productId = 12345
        ProductPrice updateProductPrice = new ProductPrice(
                id: 12345,
                name: 'pink piggy bank girls toy',
                currentPrice:  new Price(
                        value: 20.0,
                        currencyCode: CurrencyCode.USD
                )
        )

        when:
        myRetailController.updateProductPrice(productId, updateProductPrice)
        then:
        1 * service.updateProductPrice(productId, updateProductPrice) >> null
        thrown(ContentNotModifiedException)
    }
}
