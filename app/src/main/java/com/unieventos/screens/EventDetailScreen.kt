package com.unieventos.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun EventDetailScreen(
    eventId: Int,
    onNavigateBack: () -> Unit
){

    Scaffold(
        topBar = {
            TopAppBarDetail(
                onNavigateBack = onNavigateBack

            )

        }
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding)
        ) {
            val model = ImageRequest.Builder(LocalContext.current)
                .data("https://loremflickr.com/400/400/theater")
                .crossfade(true)
                .build()

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = model ,
                contentDescription = "",
                contentScale = ContentScale.Crop)
            Text(text = "Event detail screen $eventId")

        }



    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDetail(
    onNavigateBack: () -> Unit
){

    TopAppBar(
        title = {  
            Text(text = "Event Detail")
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack)
            {
            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
            }
        }
    )

}