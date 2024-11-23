package com.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.unieventos.dto.EventDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _events = MutableStateFlow<List<EventDTO>>(emptyList())
    val events: StateFlow<List<EventDTO>> get() = _events

    fun getEvents() {
        viewModelScope.launch {
            val snapshot = db.collection("events").get().await()
            val eventsList = snapshot.documents.mapNotNull { doc ->
                doc.toObject(EventDTO::class.java)
            }
            _events.value = eventsList
        }
    }

    fun createEvent(event: EventDTO) {
        viewModelScope.launch {
            db.collection("events").add(event)
        }
    }

    fun updateEvent(eventId: String, updatedEvent: EventDTO) {
        viewModelScope.launch {
            db.collection("events").document(eventId).set(updatedEvent)
        }
    }

    fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            db.collection("events").document(eventId).delete()
        }
    }
}