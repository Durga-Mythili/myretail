package com.myretail;

import com.myretail.util.CurrencyCode;
import lombok.Data;

@Data
public class Price {
    double value;
    CurrencyCode currencyCode;
}
