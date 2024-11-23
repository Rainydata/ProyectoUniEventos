package com.unieventos.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.unieventos.navigation.viewmodel.AuthViewModel
import com.unieventos.navigation.components.RegisterForm

@Composable
fun RegisterScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    Scaffold(
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                RegisterForm(
                    onRegisterClick = { email, password, additionalDetails ->
                        authViewModel.registerUser(email, password, additionalDetails)
                    }
                )
            }
        }
    )
}