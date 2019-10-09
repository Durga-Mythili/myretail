package com.myretail.controllers

import com.myretail.ProductPrice
import com.myretail.exceptions.ConflictException
import com.myretail.exceptions.ContentNotModifiedException
import com.myretail.exceptions.ProductNotFoundException
import com.myretail.service.MyRetailService
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class MyRetailController {
    MyRetailService service

    MyRetailController(MyRetailService service) {
        this.service = service
    }

    /*
    * Sets up prices of products -> which  can be obtained from some service
    * */
    @GetMapping("/")
    String welcome() {
        service.setupPriceOfProducts()
        log.info("initializing input in database")
        return "Welcome to myRetail"
    }

    /*
    * Returns price of product for given productId
    * */
    @GetMapping("/product/{id}")
    ProductPrice getProduct(@PathVariable(name = "id") int productId) {
        log.info("Obtained request for product details ${productId}")
        ProductPrice productPrice =  service.getProductPrice(productId)
        if (!productPrice) {
            log.error("Product information not found id: ${productId}")
            throw new ProductNotFoundException("Product ${productId} not found")
        }
        return productPrice
    }

    /*
    * Updates the price for given Product and productId
    * */
    @PutMapping("/product/{id}")
    ProductPrice updateProductPrice(@PathVariable(name = "id") int productId,
                                    @RequestBody ProductPrice productPrice) {
        log.info("Obtained request to update product details: ${productId}")
        if (productPrice.currentPrice.value == 0) {
            log.warn("Cannot update price: ${productPrice.currentPrice.value} for productId: ${productId}")
            throw new ConflictException("item price cannot be zero")
        }
        if (productId != productPrice.id) {
            log.warn("Update product details does not match for productId: ${productId} and update: ${productPrice}")
            throw new ConflictException("productId ${productId} doesnot match updating id ${productPrice.id}")
        }
        ProductPrice updatedPrice = service.updateProductPrice(productId, productPrice)
        if (!updatedPrice) {
            log.error("Cannot update proce for: ${productPrice}")
            throw new ContentNotModifiedException("Product price is not updated")
        }
        return updatedPrice
    }
}
