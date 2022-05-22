package com.example.ebiznesprojekt.controllers

import com.example.ebiznesprojekt.dao.ProductDao
import com.example.ebiznesprojekt.models.Product
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class ProductController {

    @GetMapping("/api/products")
    fun getProducts(): List<Product> {
        return ProductDao().getAllProducts()
    }
}