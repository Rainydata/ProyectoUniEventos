package com.unieventos.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.unieventos.navigation.viewmodel.EventViewModel
import com.unieventos.navigation.components.EventListItem
import com.unieventos.navigation.components.CreateEventForm

@Composable
fun AdminScreen(navController: NavHostController, eventViewModel: EventViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Panel") }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Mostrar lista de eventos
                val events by eventViewModel.events.collectAsState(initial = emptyList())
                LazyColumn {
                    items(events) { event ->
                        EventListItem(event = event) { selectedEvent ->
                            // Aquí puedes mostrar detalles del evento al hacer clic
                        }
                    }
                }

                // Crear nuevo evento
                CreateEventForm { newEvent ->
                    eventViewModel.createEvent(newEvent)
                }
            }
        }
    )
}