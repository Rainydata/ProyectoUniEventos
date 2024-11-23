package com.unieventos.model

data class Coupon(
    var id: Int = 0,
    val name: String,
    val discount: Double,
    val expiryDate: String
)