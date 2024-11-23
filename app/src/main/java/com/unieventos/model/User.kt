package com.unieventos.model

data class User(
    var id: String = "",
    val cedula: String = "",
    val name: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val role: Role = Role.CLIENT
)
