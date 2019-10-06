package com.myretail.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CurrencyCode {
    USD("USD"),
    INR("INR"),
    CAD("CAD");

    private String code;

    CurrencyCode(String code) {
        this.code = code;
    }

    @JsonValue
    final String getCode() {
        return code;
    }
}
