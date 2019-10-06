package com.myretail.controllers

import com.myretail.ProductPrice
import com.myretail.service.MyRetailService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
        return  service.getProduct(productId)

    }
}
