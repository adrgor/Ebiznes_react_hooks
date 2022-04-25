package com.example.ebiznesprojekt.dao

import com.example.ebiznesprojekt.models.Basket

class BasketDao {
    val basket = Basket(1, mutableListOf())

    fun addToBasket(id: Int) {
        basket.products.add(ProductDao().getProductById(id))
    }

}