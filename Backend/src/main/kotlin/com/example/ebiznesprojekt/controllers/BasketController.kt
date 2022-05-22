package com.example.ebiznesprojekt.controllers

import com.example.ebiznesprojekt.models.Product
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class BasketController {

    @PostMapping("/api/basket")
    fun postBasket(@RequestBody body: List<Product>) {
        println("Basket state received:")
        println(body)
    }
}