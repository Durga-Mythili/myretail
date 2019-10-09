package com.myretail.service

import com.myretail.Price
import com.myretail.ProductPrice
import com.myretail.dto.CurrentProductPrice
import com.myretail.redsky.RedSkyProduct
import com.myretail.repository.CurrentProductPriceRepository
import com.myretail.repository.CustomProductPriceRepoImpl
import com.myretail.util.CurrencyCode
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class MyRetailService {
    RedskyClient redskyClient
    CurrentProductPriceRepository productPriceRepository
    CustomProductPriceRepoImpl customProductPriceRepo

    MyRetailService(RedskyClient redskyClient, CurrentProductPriceRepository productPriceRepository,
                    CustomProductPriceRepoImpl customProductPriceRepo) {
        this.redskyClient = redskyClient
        this.productPriceRepository = productPriceRepository
        this.customProductPriceRepo = customProductPriceRepo
    }

    /*
    * Sets up mongo database with initial prices for products
    * */
    void setupPriceOfProducts(){
        productPriceRepository.saveAll(
                [new CurrentProductPrice(
                        productId: 13860428,
                        price: 23.toDouble(),
                        currencyCode: CurrencyCode.USD
                ),
                 new CurrentProductPrice(
                         productId: 123456,
                         price: 23.toDouble(),
                         currencyCode: CurrencyCode.USD
                 )])
    }

    /*
   * tries to obtain redsky product information and price information from database
   * returns product information and price
   * */
    ProductPrice getProductPrice(int productId) {
        RedSkyProduct redSkyProduct = redskyClient.productDetails(productId)
        log.debug("Obtained redSky product: ${redSkyProduct}")
        if (redSkyProduct) {
            CurrentProductPrice productPrice = productPriceRepository.findByProductId(productId)
            log.debug("Obtained productPrice: ${productPrice}")
            if(productPrice) {
                return new ProductPrice(
                        id: redSkyProduct.product.productAvailability.id.toInteger(),
                        name: redSkyProduct.product.item.description.title,
                        currentPrice: new Price(
                                value: productPrice.price,
                                currencyCode: productPrice.currencyCode
                        )
                )
            }
        }
        log.error("Product price is not determined for productId: ${productId}")
        return null
    }


    /*
   * tries to obtain redsky product information and update price information in database
   * returns updated product information and price
   * */
    ProductPrice updateProductPrice(int productId, ProductPrice productPrice) {
        RedSkyProduct redSkyProduct = redskyClient.productDetails(productId)
        if(redSkyProduct) {
            log.debug("Obtained redSky product to update price: ${redSkyProduct}")
            if (productPrice.id == redSkyProduct.product.productAvailability.id.toInteger()) {
                int result = customProductPriceRepo.updatePrice(productId, productPrice.currentPrice.value)
                if (result == 1) {
                    log.debug("Updated price of product ${productPrice}")
                    CurrentProductPrice currentPrice = productPriceRepository.findByProductId(productId)
                    return  new ProductPrice(
                            id: redSkyProduct.product.productAvailability.id.toInteger(),
                            name: redSkyProduct.product.item.description.title,
                            currentPrice: new Price(
                                    value: currentPrice.price,
                                    currencyCode: currentPrice.currencyCode
                            )
                    )
                }
                log.error("Error in update productId: ${productId}, product information ${productPrice}")
                return  null
            }
        }
    }
}
