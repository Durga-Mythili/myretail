package com.myretail.dto;

import com.myretail.util.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product_prices")
public class CurrentProductPrice {
    @Id  int productId;
    double price;
    CurrencyCode currencyCode;
}
