package com.example.ebiznesprojekt.models

data class PaymentDetails(val email: String, val firstName: String, val lastName: String, val street: String,
                          val buildingNumber: Int, val apartmentNumber: Int, val city: String, val postalCode: String,
                          val country: String, val phoneNumber: Int, val cardNumber: String, val expDate: String,
                          val nameOnCard: String, val cvv: Int)
