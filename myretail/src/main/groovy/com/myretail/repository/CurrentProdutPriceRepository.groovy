package com.myretail.repository

import com.myretail.dto.CurrentProductPrice
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrentProdutPriceRepository extends MongoRepository<CurrentProductPrice, Integer> {
    CurrentProductPrice findByProductId(int productId)
}
