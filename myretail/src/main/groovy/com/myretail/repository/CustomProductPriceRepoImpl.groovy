package com.myretail.repository

import com.mongodb.client.result.UpdateResult
import com.myretail.dto.CurrentProductPrice
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class CustomProductPriceRepoImpl implements  CustomProductPriceRepository {
    protected static final String KEY = 'productId'
    protected static final String PRICE = 'price'

    MongoTemplate mongoTemplate

    CustomProductPriceRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate
    }

    @Override
    int updatePrice(int productId, double price) {
        Query query = new Query(Criteria.where(KEY).is(productId))
        Update update = new Update()
        update.set(PRICE, price)
        UpdateResult result = mongoTemplate.updateFirst(query, update, CurrentProductPrice)
        if(result != null) {
            return result.modifiedCount
        }
        return 0
    }
}
