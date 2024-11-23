package com.unieventos.model

data class Coupon(
    var id: String = "",
    val name: String = "",
    val discount: Double = 0.0,
    val expiryDate: String = ""
)