package com.unieventos.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.unieventos.ui.components.ItemEvento


@Composable
fun HomeScreen(
    onNavigationToEditProfile: () -> Unit

){
    Scaffold {
            paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            ItemEvento(
                "Obra de Teateo 1",
                "Pereira"
            )

            ItemEvento(
                "Concierto Andrés Cepeda",
                "Bogotá"

            )
            ItemEvento(
                "Ópera",
                "Cali"

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
