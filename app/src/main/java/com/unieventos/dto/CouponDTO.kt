package com.unieventos.dto

data class CouponDTO(
    val id: String = "",
    val code: String = "",
    val discount: Int = 0,
    val eventId: String = ""
)