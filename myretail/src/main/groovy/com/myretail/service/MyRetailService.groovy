package com.myretail.service

import com.myretail.Price
import com.myretail.ProductPrice
import com.myretail.dto.CurrentProductPrice
import com.myretail.redsky.RedSkyProduct
import com.myretail.repository.CurrentProdutPriceRepository
import com.myretail.repository.CustomProductPriceRepoImpl
import org.springframework.stereotype.Service

@Service
class MyRetailService {
    RedskyClient redskyClient
    CurrentProdutPriceRepository productPriceRepository
    CustomProductPriceRepoImpl customProductPriceRepo

    MyRetailService(RedskyClient redskyClient, CurrentProdutPriceRepository productPriceRepository,
    CustomProductPriceRepoImpl customProductPriceRepo) {
        this.redskyClient = redskyClient
        this.productPriceRepository = productPriceRepository
        this.customProductPriceRepo = customProductPriceRepo
    }

    ProductPrice getProductPrice(int productId) {
        ProductPrice price  = null
        RedSkyProduct redSkyProduct = redskyClient.productDetails(productId)
        if (redSkyProduct) {
          /*  productPriceRepository.save(new CurrentProductPrice(
                    productId: productId,
                    price: 23.toDouble(),
                    currencyCode: CurrencyCode.USD
            ))
*/
           CurrentProductPrice productPrice = productPriceRepository.findByProductId(productId)
            if(productPrice) {
             price = new ProductPrice(
                        id: redSkyProduct.product.productAvailability.id.toInteger(),
                        name: redSkyProduct.product.item.description.title,
                        currentPrice: new Price(
                                value: productPrice.price,
                                currencyCode: productPrice.currencyCode
                        )
                )
            }
        }
        return price
    }

    ProductPrice updateProductPrice(int productId, ProductPrice productPrice) {
        RedSkyProduct redSkyProduct = redskyClient.productDetails(productId)
        if(redSkyProduct) {
            if (productPrice.id == redSkyProduct.product.productAvailability.id.toInteger()) {
                int result = customProductPriceRepo.updatePrice(productId, productPrice.currentPrice.value)
                if (result == 1) {
                    CurrentProductPrice currentPrice = productPriceRepository.findByProductId(productId)
                   return  new ProductPrice(
                           id: redSkyProduct.product.productAvailability.id.toInteger(),
                           name: redSkyProduct.product.item.description.title,
                           currentPrice: new Price(
                                   value: currentPrice.price,
                                   currencyCode: currentPrice.currencyCode
                           )
                   )
                }
                return  null
            }
        }
    }
}
