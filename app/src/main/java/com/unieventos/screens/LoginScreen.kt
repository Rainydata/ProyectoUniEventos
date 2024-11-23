package com.unieventos.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.unieventos.navigation.viewmodel.AuthViewModel
import com.unieventos.navigation.components.LoginForm

@Composable
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    Scaffold(
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoginForm(
                    onLoginClick = { email, password ->
                        authViewModel.loginUser(email, password)
                    },
                    onForgotPasswordClick = {
                        navController.navigate("passwordRecovery")
                    }
                )
            }
        }
    )
}