package com.myretail.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.ACCEPTED)
class ContentNotModifiedException extends RuntimeException{
    ContentNotModifiedException(String message) {
        super(message)
    }
}
