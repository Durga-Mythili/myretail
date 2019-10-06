package com.myretail.service

import com.myretail.Price
import com.myretail.ProductPrice
import com.myretail.redsky.RedSkyProduct
import com.myretail.util.CurrencyCode
import org.springframework.stereotype.Service

@Service
class MyRetailService {
    RedskyClient redskyClient

    MyRetailService(RedskyClient redskyClient) {
        this.redskyClient = redskyClient
    }

   ProductPrice getProduct(int productId) {
       RedSkyProduct redSkyProduct = redskyClient.productDetails(productId)
       if (redSkyProduct) {
           // connect to Mongo
           return new ProductPrice(
                   id: redSkyProduct.product.availableToPromiseNetwork.productId.toInteger(),
                   name: redSkyProduct.product.item.productDescription.title,
                   currentPrice: new Price(
                           value: 123.24,
                           currencyCode: CurrencyCode.USD
                   )
           )
       }
       return null
   }
}
