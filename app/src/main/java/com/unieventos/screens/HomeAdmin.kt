package com.unieventos.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.LocalActivity
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.unieventos.model.Event
import com.unieventos.navigation.RouteScreen
import com.unieventos.viewmodel.EventsViewModel


@Composable
fun HomeAdmin(
    onNavigationToEditProfile: () -> Unit,
    onNavigationToCreateCoupon: () -> Unit,
    onNavigationToEventDetail: (String) -> Unit,
    onNavigationToCreateEvent: () -> Unit,
    onNavigationToCouponScreen: () -> Unit,
    eventsViewModel: EventsViewModel, // ViewModel que gestiona los eventos
    navController: NavController
) {
    // Obtener el estado de los eventos desde el ViewModel
    val events by eventsViewModel.events.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    // Filtrar eventos según la búsqueda
    val filteredEvents = if (searchQuery.isEmpty()) {
        events
    } else {
        events.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        bottomBar = { BottomBarHomeAdmin(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Panel de Administración",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            // Botones para gestionar eventos y cupones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onNavigationToCreateEvent() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Gestionar Eventos")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onNavigationToCreateCoupon() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Crear Cupones")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Campo de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query -> searchQuery = query },
                label = { Text("Buscar Eventos") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Encabezado de lista de eventos
            Text(
                text = "Eventos Creados",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp)
            )

            // Lista de eventos creados
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredEvents) { event ->
                    EventItem(
                        event = event,
                        onDelete = {
                            eventsViewModel.deleteEvent(event.id)
                        },
                        onEdit = {
                            onNavigationToEventDetail(event.id)
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun BottomBarHomeAdmin(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.LocalActivity,
                    contentDescription = null
                )
            },
            onClick = {
                navController.navigate(RouteScreen.CreateEvent) // Navegar a la pantalla de eventos
            },
            label = {
                Text(text = "Eventos")
            },
            selected = false
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Archive,
                    contentDescription = null
                )
            },
            onClick = {
                navController.navigate(RouteScreen.CouponScreen) // Navegar a la pantalla de cupones
            },
            label = {
                Text(text = "Cupones")
            },
            selected = false
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = null
                )
            },
            onClick = {
                navController.navigate(RouteScreen.EditProfile) // Navegar a la pantalla de perfil
            },
            label = {
                Text(text = "Perfil")
            },
            selected = false
        )
    }
}


// Composable para mostrar una tarjeta de evento
@Composable
fun EventItem(event: Event, onDelete: () -> Unit, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Nombre: ${event.title}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Fecha: ${event.date}", style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Evento")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar Evento")
                }
            }
        }
    }
}
