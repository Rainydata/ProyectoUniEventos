package com.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unieventos.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventsViewModel: ViewModel() {

    val db = Firebase.firestore
    private val _events = MutableStateFlow(emptyList<Event>())
    val events : StateFlow<List<Event>> = _events.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents(){
        viewModelScope.launch {
            _events.value = getEventsList()
        }
    }

    private suspend fun getEventsList(): List<Event>{
        val snapshot = db.collection("events")
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            val event = it.toObject(Event::class.java)
            requireNotNull(event)
            event.id = it.id
            event
        }
    }

    suspend fun createEvent(event: Event){
        viewModelScope.launch {
            db.collection("events")
                .add(event)
                .await()
        }
    }

    fun deleteEvent(event: Event){
        _events.value -= event

    }

    fun findEventById(id: Int): Event?{
        return null
    }

    fun searchEvents(query: String): List<Event>{
    return listOf()
    }

}