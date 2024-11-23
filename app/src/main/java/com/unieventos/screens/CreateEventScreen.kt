package com.unieventos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.unieventos.model.Event
import com.unieventos.viewmodel.EventsViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.unieventos.utils.RequestResult
import kotlinx.coroutines.delay


@Composable
fun CreateEventScreen(
    onNavigationBack: () -> Unit,
    eventsViewModel: EventsViewModel,
    eventId: String? = null
) {
    val context = LocalContext.current

    var event: Event? by remember { mutableStateOf(null) }

    var title by rememberSaveable { mutableStateOf("") }
    var place by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var opening by rememberSaveable { mutableStateOf("") }
    var image by rememberSaveable { mutableStateOf("") }

    var isFormValid by rememberSaveable { mutableStateOf(false) }

    val eventCreationResult by eventsViewModel.eventCreationResult.collectAsState()

    if (eventId != null) {
        LaunchedEffect(eventId) {
            event = eventsViewModel.findEventById(eventId)
            event?.let {
                title = it.title
                place = it.place
                city = it.city
                date = it.date
                time = it.time
                category = it.category
                address = it.address
                opening = it.opening
                image = it.image
            }
        }
    }

    LaunchedEffect(title, place, city, date, time, category, address, opening, image) {
        isFormValid = title.isNotBlank() && place.isNotBlank() && city.isNotBlank() &&
                date.matches(Regex("\\d{4}-\\d{2}-\\d{2}")) &&
                time.matches(Regex("\\d{2}:\\d{2}")) &&
                category.isNotBlank() && address.isNotBlank() &&
                opening.matches(Regex("\\d+(\\.\\d{1,2})?")) // Valida apertura como un número
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Campo para el título
            EventField(
                label = "Título",
                value = title,
                onValueChange = { title = it }
            )
            // Campo para el lugar
            EventField(
                label = "Lugar",
                value = place,
                onValueChange = { place = it }
            )
            // Campo para la ciudad
            EventField(
                label = "Ciudad",
                value = city,
                onValueChange = { city = it }
            )
            // Campo para la fecha
            EventField(
                label = "Fecha (YYYY-MM-DD)",
                value = date,
                onValueChange = { date = it }
            )
            // Campo para la hora
            EventField(
                label = "Hora (HH:mm)",
                value = time,
                onValueChange = { time = it }
            )
            // Campo para la categoría
            EventField(
                label = "Categoría",
                value = category,
                onValueChange = { category = it }
            )
            // Campo para la dirección
            EventField(
                label = "Dirección",
                value = address,
                onValueChange = { address = it }
            )
            // Campo para la apertura
            EventField(
                label = "Apertura",
                value = opening,
                onValueChange = { opening = it }
            )
            // Campo para la imagen
            EventField(
                label = "Imagen (URL)",
                value = image,
                onValueChange = { image = it }
            )

            // Botón para guardar el evento
            Button(
                onClick = {
                    eventsViewModel.createEvent(
                        Event(
                            id = event?.id ?: "",
                            title = title,
                            place = place,
                            city = city,
                            date = date,
                            time = time,
                            category = category,
                            address = address,
                            opening = opening,
                            image = image
                        )
                    )
                },
                enabled = isFormValid
            ) {
                Text(text = "Guardar Evento")
            }

            // Estado del resultado de creación del evento
            when (eventCreationResult) {
                is RequestResult.Loading -> {
                    CircularProgressIndicator()
                }
                is RequestResult.Success -> {
                    Text(
                        text = (eventCreationResult as RequestResult.Success).message,
                        color = MaterialTheme.colorScheme.primary
                    )
                    LaunchedEffect(Unit) {
                        delay(1200)
                        onNavigationBack()
                        eventsViewModel.resetEventCreationResult()
                    }
                }
                is RequestResult.Failure -> {
                    Text(
                        text = (eventCreationResult as RequestResult.Failure).messageError,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                null -> {}
            }
        }
    }
}


// Composable for an event field with label and text input
@Composable
fun EventField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onDone = { /* Action when done */ }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                .padding(12.dp)
        )
    }
}

