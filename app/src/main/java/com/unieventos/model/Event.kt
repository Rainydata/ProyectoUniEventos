package com.unieventos.model

import java.util.Date

data class Event(
    var id: String = "",
    val title: String = "",
    val place: String = "",
    val city: String = "",
    val date: String = "",
    val time: String = "",
    val category: String = "",
    val address: String = "",
    val opening: String = "",
    val image: String = "",
    val type: EventType = EventType.DEFAULT,
    val locations: List<Location> = listOf()
){
    fun getLocation(nombre: String): Location? {
        return locations.find { it.name == nombre }
    }
}
