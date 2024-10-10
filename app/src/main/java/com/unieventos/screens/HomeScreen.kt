package com.unieventos.screens

import ItemEvento
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.unieventos.model.Event
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild


@Composable
fun HomeScreen(
    onNavigationToEditProfile: () -> Unit,
    onNavigationToEventDetail:(Int) -> Unit

){

    val hazeState = remember{ HazeState() }

    Scaffold(
        topBar = {
            TopBarHome(
                hazeState= hazeState
            )
        }
    ) {
            paddingValues ->
            EventsList(
                onNavigationToEventDetail = onNavigationToEventDetail,
                paddingValues= paddingValues,
                hazeState = hazeState
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome(
    hazeState: HazeState
){
CenterAlignedTopAppBar(
    modifier = Modifier.hazeChild(state =hazeState),
    title = {
    Text(text = "UniEventos")
},
)
}

@Composable
fun EventsList(
    paddingValues : PaddingValues,
    onNavigationToEventDetail: (Int) -> Unit,
    hazeState: HazeState
    ){

    LazyColumn(
        modifier = Modifier
            .haze(
                state = hazeState,
                style = HazeDefaults.style(backgroundColor = MaterialTheme.colorScheme.surface)
            )
        ,
        contentPadding = paddingValues
    ) {
        items( getEventsList() ){
            event ->
            ItemEvento(
                event = event,
                onNavigationToEventDetail = onNavigationToEventDetail
            )
        }
    }
}

fun getEventsList(): List<Event>{
    return listOf(
        Event(
            id = 1,
            place = "Teatro Municipal",
            title = "Evento1",
            city = "Medellin",
            date = "12/05/2023",
            time = "19:00",
            category = "Teatro",
            address = "Calle 1",
            opening = "19:00",
            image = "https://loremflickr.com/400/400/theater"
        ),
        Event(
            id = 2,
            place = "Teatro Departamental",
            title = "Evento2",
            city = "Medellin",
            date = "12/05/2023",
            time = "19:00",
            category = "Teatro",
            address = "Calle 2",
            opening = "19:00",
            image = "https://loremflickr.com/400/400/theater"
        )
    )
}

//@Composable
//fun HomeScreen(
//    onNavigationToEditProfile: () -> Unit
//) {
//    val context = LocalContext.current
//
//    Scaffold { padding ->
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Button(
//                onClick = {
//                    onNavigationToEditProfile()
//                }
//            ) {
//                Text(text = "Editar perfil")
//            }
//        }
//    }
//}
