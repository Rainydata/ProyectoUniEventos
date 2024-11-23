package com.unieventos.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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

@Composable
fun CreateEventScreen(
    onEventCreated: (Event) -> Unit,
    onNavigationBack: () -> Unit,
    eventsViewModel: EventsViewModel
) {
    // States for event fields
    val title = remember { mutableStateOf("") }
    val place = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val opening = remember { mutableStateOf("") }
    val image = remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Screen title
            Text(
                text = "Create New Event",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            // Fields for event details
            EventField(
                label = "Title",
                value = title.value,
                onValueChange = { title.value = it }
            )
            EventField(
                label = "Place",
                value = place.value,
                onValueChange = { place.value = it }
            )
            EventField(
                label = "City",
                value = city.value,
                onValueChange = { city.value = it }
            )
            EventField(
                label = "Date",
                value = date.value,
                onValueChange = { date.value = it }
            )
            EventField(
                label = "Time",
                value = time.value,
                onValueChange = { time.value = it }
            )
            EventField(
                label = "Category",
                value = category.value,
                onValueChange = { category.value = it }
            )
            EventField(
                label = "Address",
                value = address.value,
                onValueChange = { address.value = it }
            )
            EventField(
                label = "Opening",
                value = opening.value,
                onValueChange = { opening.value = it }
            )
            EventField(
                label = "Image (URL)",
                value = image.value,
                onValueChange = { image.value = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Button to save the event
            Button(
                onClick = {
                    // Create event
                    val newEvent = Event(
                        id = "", // ID assigned dynamically in ViewModel or database
                        title = title.value,
                        place = place.value,
                        city = city.value,
                        date = date.value,
                        time = time.value,
                        category = category.value,
                        address = address.value,
                        opening = opening.value,
                        image = image.value
                    )
                    // Call the function to pass the created event
                    onEventCreated(newEvent)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text(text = "Save Event", color = Color.White)
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

