package com.unieventos.model

data class Coupon(
    var id: String = "",
    val name: String,
    val discount: Double,
    val expiryDate: String
)