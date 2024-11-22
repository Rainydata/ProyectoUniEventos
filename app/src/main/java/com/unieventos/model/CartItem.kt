package com.unieventos.model

data class CartItem(
    val id: Int,
    val eventId: Int,
    val eventName: String,
    val eventImageUrl: String,
    val tickets: Int
)
