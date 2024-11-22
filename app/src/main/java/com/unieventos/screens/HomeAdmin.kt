package com.unieventos.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unieventos.viewmodel.EventsViewModel

@Composable
fun HomeAdmin(
    onNavigationToEditProfile: () -> Unit,
    onNavigationToCreateCoupon: () -> Unit,
    onNavigationToEventDetail: (String) -> Unit,
    eventsViewModel: EventsViewModel
) {

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Título del panel de administración
            Text(
                text = "Panel de Administración",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            // Botones para las acciones principales
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Lógica para gestionar eventos */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Gestionar Eventos")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { /* Lógica para crear nuevos cupones */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Crear Cupones")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sección de lista de eventos creados
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

                }
            }
        }
    }


// Composable para mostrar una tarjeta de evento
@Composable
fun TarjetaEvento(nombreEvento: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nombre del evento
            Text(
                text = nombreEvento,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Button(
                onClick = { /* Lógica para editar el evento */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text(text = "Editar", color = Color.White)
            }
        }
    }
}
