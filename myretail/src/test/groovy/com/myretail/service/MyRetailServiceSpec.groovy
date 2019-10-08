package com.myretail.service

import com.myretail.Price
import com.myretail.ProductPrice
import com.myretail.dto.CurrentProductPrice
import com.myretail.redsky.Item
import com.myretail.redsky.Product
import com.myretail.redsky.ProductAvailability
import com.myretail.redsky.ProductDescription
import com.myretail.redsky.RedSkyProduct
import com.myretail.repository.CurrentProdutPriceRepository
import com.myretail.repository.CustomProductPriceRepoImpl
import com.myretail.util.CurrencyCode
import spock.lang.Specification

class MyRetailServiceSpec extends Specification {

    RedskyClient redskyClient
    CurrentProdutPriceRepository productPriceRepository

    RedSkyProduct redSkyProduct

    CurrentProductPrice itemPrice
    MyRetailService retailService
    ProductPrice productPrice
    CustomProductPriceRepoImpl customProductPriceRepo

    void setup() {
        productPriceRepository = Mock()
        redskyClient = Mock()
        customProductPriceRepo = Mock()

        retailService = new MyRetailService(redskyClient, productPriceRepository, customProductPriceRepo)

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

        itemPrice = new CurrentProductPrice(
                productId: 12345,
                price: 20.0,
                currencyCode: CurrencyCode.USD
        )

        productPrice = new ProductPrice(
                id: 12345,
                name: 'pink piggy bank girls toy',
                currentPrice:  new Price(
                        value: 30.0,
                        currencyCode: CurrencyCode.USD
                )
        )
    }

    void 'I can return prices of product'() {
        given:
        int productId = 12345

        when:
        ProductPrice productPrice = retailService.getProductPrice(productId)

        then:
        1 * redskyClient.productDetails(productId) >> redSkyProduct
        1 * productPriceRepository.findByProductId(productId) >> itemPrice
        productPrice.id == productId
        productPrice.name == redSkyProduct.product.item.description.title
        productPrice.currentPrice.value == itemPrice.price
        productPrice.currentPrice.currencyCode == itemPrice.currencyCode
    }

    void 'I cannot return price if I dont know product details'() {
        int productId = 12345

        when:
        ProductPrice productPrice = retailService.getProductPrice(productId)

        then:
        1 * redskyClient.productDetails(productId) >> null
        0 * productPriceRepository.findByProductId(productId) >> itemPrice
        productPrice == null
    }
    void 'I cannot return price if I dont know price details'() {
        int productId = 12345

        when:
        ProductPrice productPrice = retailService.getProductPrice(productId)

        then:
        1 * redskyClient.productDetails(productId) >> redSkyProduct
        1 * productPriceRepository.findByProductId(productId) >> null
        productPrice == null
    }

    void 'I can update price of product'() {
        int productId = productPrice.id

        CurrentProductPrice  updatedPrice = new CurrentProductPrice(
                productId: 12345,
                price: 20.0,
                currencyCode: CurrencyCode.USD
        )

        when:
        ProductPrice  updatedProductPrice = retailService.updateProductPrice(productId, productPrice)

        then:
        1 * redskyClient.productDetails(productId) >> redSkyProduct
        1 * customProductPriceRepo.updatePrice(productId, productPrice.currentPrice.value) >> 1
        1 * productPriceRepository.findByProductId(productId) >> updatedPrice
        updatedProductPrice.name == redSkyProduct.product.item.description.title
        updatedProductPrice.id == redSkyProduct.product.productAvailability.id.toInteger()
        updatedProductPrice.currentPrice.value == updatedPrice.price
        updatedProductPrice.currentPrice.currencyCode == updatedPrice.currencyCode
    }

    void 'I cannot update price of product if product id do not match'() {
        int productId = 123

        when:
        ProductPrice  updatedProductPrice = retailService.updateProductPrice(productId, productPrice)

        then:
        1 * redskyClient.productDetails(productId) >> redSkyProduct
        !updatedProductPrice
    }

    void 'I will return empty object if I cannot update the price'() {
        int productId = productPrice.id

        when:
        ProductPrice  updatedProductPrice = retailService.updateProductPrice(productId, productPrice)

        then:
        1 * redskyClient.productDetails(productId) >> redSkyProduct
        1 * customProductPriceRepo.updatePrice(productId, productPrice.currentPrice.value) >> 0
       !updatedProductPrice
    }

}
