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

    // Estados para errores de validación
    var titleError by rememberSaveable { mutableStateOf<String?>(null) }
    var placeError by rememberSaveable { mutableStateOf<String?>(null) }
    var cityError by rememberSaveable { mutableStateOf<String?>(null) }
    var dateError by rememberSaveable { mutableStateOf<String?>(null) }
    var timeError by rememberSaveable { mutableStateOf<String?>(null) }
    var categoryError by rememberSaveable { mutableStateOf<String?>(null) }
    var addressError by rememberSaveable { mutableStateOf<String?>(null) }
    var openingError by rememberSaveable { mutableStateOf<String?>(null) }

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
            ValidatedTextField(
                label = "Título",
                value = title,
                error = titleError,
                onValueChange = {
                    title = it
                    titleError = if (it.isBlank()) "El título no puede estar vacío" else null
                }
            )
            // Campo para el lugar
            ValidatedTextField(
                label = "Lugar",
                value = place,
                error = placeError,
                onValueChange = {
                    place = it
                    placeError = if (it.isBlank()) "El lugar no puede estar vacío" else null
                }
            )
            // Campo para la ciudad
            ValidatedTextField(
                label = "Ciudad",
                value = city,
                error = cityError,
                onValueChange = {
                    city = it
                    cityError = if (it.isBlank()) "La ciudad no puede estar vacía" else null
                }
            )
            // Campo para la fecha
            ValidatedTextField(
                label = "Fecha (YYYY-MM-DD)",
                value = date,
                error = dateError,
                onValueChange = {
                    date = it
                    dateError = if (!it.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) "Fecha inválida" else null
                }
            )
            // Campo para la hora
            ValidatedTextField(
                label = "Hora (HH:mm)",
                value = time,
                error = timeError,
                onValueChange = {
                    time = it
                    timeError = if (!it.matches(Regex("\\d{2}:\\d{2}"))) "Hora inválida" else null
                }
            )
            // Campo para la categoría
            ValidatedTextField(
                label = "Categoría",
                value = category,
                error = categoryError,
                onValueChange = {
                    category = it
                    categoryError = if (it.isBlank()) "La categoría no puede estar vacía" else null
                }
            )
            // Campo para la dirección
            ValidatedTextField(
                label = "Dirección",
                value = address,
                error = addressError,
                onValueChange = {
                    address = it
                    addressError = if (it.isBlank()) "La dirección no puede estar vacía" else null
                }
            )
            // Campo para la apertura
            ValidatedTextField(
                label = "Apertura",
                value = opening,
                error = openingError,
                onValueChange = {
                    opening = it
                    openingError = if (!it.matches(Regex("\\d+(\\.\\d{1,2})?"))) "Apertura inválida" else null
                }
            )

            // Botón para guardar el evento
            Button(
                onClick = {
                    if (titleError == null && placeError == null && cityError == null &&
                        dateError == null && timeError == null && categoryError == null &&
                        addressError == null && openingError == null
                    ) {
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
                    }
                },
                enabled = titleError == null && placeError == null && cityError == null &&
                        dateError == null && timeError == null && categoryError == null &&
                        addressError == null && openingError == null
            ) {
                Text(text = "Guardar Evento")
            }
        }
    }
}

@Composable
fun ValidatedTextField(
    label: String,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
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

