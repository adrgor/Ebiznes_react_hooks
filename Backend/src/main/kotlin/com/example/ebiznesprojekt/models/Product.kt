package com.example.ebiznesprojekt.models

data class Product (val id: Int, val name: String, val category: String = "Unassigned", val price: Float)