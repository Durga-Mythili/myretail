package com.myretail.dto;

import com.myretail.util.CurrencyCode;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "itemPrices")
public class ItemPrice {
    @Id
    int productId;
    double price;
    CurrencyCode currencyCode;
}
