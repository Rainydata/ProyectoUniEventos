package com.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import com.unieventos.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventsViewModel : ViewModel() {

    private val _events = MutableStateFlow(emptyList<Event>())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    init {
        _events.value = getEventsList()

    }

    fun createEvent(event: Event) {
        _events.value += event

    }

    fun deleteEvent(event: Event) {
        _events.value -= event

    }

    fun findEventById(id: Int): Event? {
        return _events.value.find { it.id == id }

    }

    fun searchEvents(query: String): List<Event> {
        return _events.value.filter { it.title.contains(query, ignoreCase = true) }
    }

    fun getEventsList(): List<Event> {
        return listOf(
            Event(
                id = 1,
                place = "Teatro Municipal",
                title = "Evento1",
                city = "Medellin",
                date = "12 Octubre",
                time = "19:00",
                category = "Teatro",
                address = "Calle 1",
                opening = "19:00",
                image = "https://loremflickr.com/400/400/theater"
            ),
            Event(
                id = 2,
                place = "Teatro Departamental",
                title = "Evento2",
                city = "Medellin",
                date = "9 Noviembre",
                time = "19:00",
                category = "Teatro",
                address = "Calle 2",
                opening = "19:00",
                image = "https://loremflickr.com/400/400/theater"
            ),
            Event(
                id = 3,
                place = "Concierto",
                title = "Evento3",
                city = "Bogotá",
                date = "11 Octubre",
                time = "19:00",
                category = "Teatro",
                address = "Calle 2",
                opening = "19:00",
                image = "https://loremflickr.com/400/400/concert"
            ),
            Event(
                id = 2,
                place = "Teatro Departamental",
                title = "Evento2",
                city = "Medellin",
                date = "10 Octubre",
                time = "19:00",
                category = "Teatro",
                address = "Calle 2",
                opening = "19:00",
                image = "https://loremflickr.com/400/400/theater"
            ),
            Event(
                id = 2,
                place = "Teatro Departamental",
                title = "Evento2",
                city = "Medellin",
                date = "1 Noviembre",
                time = "20:00",
                category = "Teatro",
                address = "Calle 2",
                opening = "19:00",
                image = "https://loremflickr.com/400/400/theater"
            ),
        )
    }


}