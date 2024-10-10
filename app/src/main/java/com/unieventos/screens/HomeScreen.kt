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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.unieventos.model.Event
import com.unieventos.viewmodel.EventsViewModel
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild


@Composable
fun HomeScreen(
    onNavigationToEditProfile: () -> Unit,
    onNavigationToEventDetail:(Int) -> Unit,
    eventsViewModel: EventsViewModel

){

    val hazeState = remember{ HazeState() }
    val events = eventsViewModel.events.collectAsState()

    Scaffold(
        topBar = {
            TopBarHome(
                hazeState= hazeState
            )
        }
    ) {
            paddingValues ->
            EventsList(
                events= events.value,
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
    colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
    modifier = Modifier.hazeChild(state =hazeState),
    title = {
    Text(text = "UniEventos")
},
)
}

@Composable
fun EventsList(
    events: List<Event>,
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
        items( events ){
            event ->
            ItemEvento(
                event = event,
                onNavigationToEventDetail = onNavigationToEventDetail
            )
        }
    }
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
