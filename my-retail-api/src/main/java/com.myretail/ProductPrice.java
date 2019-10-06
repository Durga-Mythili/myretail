package com.myretail;

import lombok.Data;

@Data
public class ProductPrice {
    int id;
    String name;
    Price currentPrice;
}
