package com.myretail.repository

interface CustomProductPriceRepository {
    int updatePrice(int productId, double price)
}
