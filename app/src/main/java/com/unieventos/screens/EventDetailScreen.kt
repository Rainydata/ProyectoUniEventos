package com.unieventos.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.unieventos.viewmodel.EventsViewModel

@Composable
fun EventDetailScreen(
    eventId: String,
    eventsViewModel: EventsViewModel,
    onNavigateBack: () -> Unit
){
    val event = eventsViewModel.findEventById(eventId)
    requireNotNull(event)
    Scaffold(
        topBar = {
            TopAppBarDetail(
                title = event.title,
                onNavigateBack = onNavigateBack

            )

        }
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding)
        ) {
            val model = ImageRequest.Builder(LocalContext.current)
                .data(event.image)
                .crossfade(true)
                .build()

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = model ,
                contentDescription = "",
                contentScale = ContentScale.Crop)
//            Text(text = "Event detail screen $eventId")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {

                Spacer(modifier = Modifier.height(15.dp))

                ItemInfoEvento(imageVector = Icons.Default.Title, text = event.title)


                ItemInfoEvento(imageVector = Icons.Default.CalendarToday, text = event.date)

                Spacer(modifier = Modifier.height(15.dp))

                ItemInfoEvento(imageVector = Icons.Default.LocationCity, text = event.city)

                Spacer(modifier = Modifier.height(15.dp))

                ItemInfoEvento(imageVector = Icons.Default.PinDrop, text = event.address)

                Spacer(modifier = Modifier.height(15.dp))

                ItemInfoEvento(imageVector = Icons.Default.Category, text = event.category)



            }

        }



    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDetail(
    title: String,
    onNavigateBack: () -> Unit
){

    TopAppBar(
        title = {  
            Text(title)
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack)
            {
            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
            }
        }
    )

}
@Composable
fun ItemInfoEvento(
    imageVector: ImageVector,
    text: String
){
    Row {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}