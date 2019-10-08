package com.myretail.controllers

import com.myretail.ProductPrice
import com.myretail.service.MyRetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MyRetailController {
    MyRetailService service

    MyRetailController(MyRetailService service) {
        this.service = service
    }

    @GetMapping("/")
    String welcome() {
        return "Welcome to myRetail"
    }

    @GetMapping("/product/{id}")
    ProductPrice getProduct(@PathVariable(name = "id") int productId) {
        println("in controller")
        return  service.getProductPrice(productId)

    }

    @PutMapping("/product/{id}")
    ProductPrice updateProductPrice(@PathVariable(name = "id") int productId,
                                    @RequestBody ProductPrice productPrice) {
        if(productId != productPrice.id) {
            return null
        }
        return  service.updateProductPrice(productId, productPrice)

    }
}
