package com.myretail.repository

import com.myretail.dto.ItemPrice
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductItemRepository extends MongoRepository<ItemPrice, Integer> {
  //  ItemPrice findBy(int itemId);
}
