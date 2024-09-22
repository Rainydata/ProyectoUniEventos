package com.unieventos.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    onNavigationToEditProfile: () -> Unit
) {
    val context = LocalContext.current

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    onNavigationToEditProfile()
                }
            ) {
                Text(text = "Editar perfil")
            }
        }
    }
}
