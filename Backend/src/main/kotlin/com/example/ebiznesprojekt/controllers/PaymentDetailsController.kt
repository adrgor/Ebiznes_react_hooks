package com.example.ebiznesprojekt.controllers

import com.example.ebiznesprojekt.models.PaymentDetails
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class PaymentDetailsController {

    @PostMapping("/api/details")
    fun postDetails(@RequestBody body: PaymentDetails) {
        println("Payment details received:")
        println(body)
    }
}