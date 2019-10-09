package com.myretail.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.NOT_FOUND)
class ProductNotFoundException extends RuntimeException {
    ProductNotFoundException(String message) {
        super(message)
    }
}
