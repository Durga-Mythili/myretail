package com.myretail.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MyRetailController {

    @GetMapping("/")
    String welcome() {
        return "Welcome to myRetail"
    }
}
