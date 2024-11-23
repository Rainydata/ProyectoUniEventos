package com.unieventos.model

import com.google.firebase.firestore.FirebaseFirestore
import com.unieventos.dto.EventDTO

object EventModel {
    private val db = FirebaseFirestore.getInstance()

    fun getEvents(onComplete: (List<EventDTO>) -> Unit) {
        db.collection("events").get().addOnSuccessListener { snapshot ->
            val events = snapshot.toObjects(EventDTO::class.java)
            onComplete(events)
        }
    }

    fun createEvent(event: EventDTO, onComplete: (Boolean, String?) -> Unit) {
        db.collection("events").add(event).addOnCompleteListener { task ->
            onComplete(task.isSuccessful, task.exception?.message)
        }
    }

    fun updateEvent(event: EventDTO, onComplete: (Boolean, String?) -> Unit) {
        db.collection("events").document(event.id).set(event).addOnCompleteListener { task ->
            onComplete(task.isSuccessful, task.exception?.message)
        }
    }

    fun deleteEvent(eventId: String, onComplete: (Boolean, String?) -> Unit) {
        db.collection("events").document(eventId).delete().addOnCompleteListener { task ->
            onComplete(task.isSuccessful, task.exception?.message)
        }
    }
}

