package com.unieventos.viewmodel

import androidx.compose.animation.core.snap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unieventos.model.Event
import com.unieventos.utils.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventsViewModel : ViewModel() {

    val db = Firebase.firestore

    // StateFlow para mantener la lista de eventos
    private val _events = MutableStateFlow(emptyList<Event>())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    // StateFlow para rastrear el resultado de creación de eventos
    private val _eventCreationResult = MutableStateFlow<RequestResult?>(null)
    val eventCreationResult: StateFlow<RequestResult?> = _eventCreationResult.asStateFlow()

    init {
        loadEvents()
    }

    // Cargar la lista de eventos desde Firebase
    fun loadEvents() {
        viewModelScope.launch {
            _events.value = getEventsList()
        }
    }

    // Crear un nuevo evento
    fun createEvent(event: Event) {
        viewModelScope.launch {
            _eventCreationResult.value = RequestResult.Loading
            try {
                db.collection("events")
                    .add(event)
                    .await()
                loadEvents()
                _eventCreationResult.value = RequestResult.Success("Evento creado exitosamente.")
            } catch (e: Exception) {
                _eventCreationResult.value = RequestResult.Failure("Error al crear el evento: ${e.message}")
            }
        }
    }

    // Eliminar un evento por su ID
    fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            try {
                db.collection("events")
                    .document(eventId)
                    .delete()
                    .await()
                loadEvents()
            } catch (e: Exception) {
                // Manejar errores según sea necesario
            }
        }
    }

    // Actualizar un evento existente
    fun updateEvent(event: Event) {
        viewModelScope.launch {
            try {
                db.collection("events")
                    .document(event.id)
                    .set(event)
                    .await()
                loadEvents()
            } catch (e: Exception) {
                // Manejar errores según sea necesario
            }
        }
    }

    // Buscar un evento por su ID
    suspend fun findEventById(id: String): Event? {
        return try {
            val snapshot = db.collection("events")
                .document(id)
                .get()
                .await()

            val event = snapshot.toObject(Event::class.java)
            event?.id = snapshot.id
            event
        } catch (e: Exception) {
            null
        }
    }

    // Reiniciar el resultado de la creación del evento
    fun resetEventCreationResult() {
        _eventCreationResult.value = null
    }

    // Buscar eventos que coincidan con una consulta (pendiente de implementación)
    fun searchEvents(query: String): List<Event> {
        return listOf() // Implementar búsqueda personalizada si es necesario
    }

    // Obtener la lista de eventos desde Firebase
    private suspend fun getEventsList(): List<Event> {
        return try {
            val snapshot = db.collection("events")
                .get()
                .await()

            snapshot.documents.mapNotNull {
                val event = it.toObject(Event::class.java)
                requireNotNull(event)
                event.id = it.id
                event
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
