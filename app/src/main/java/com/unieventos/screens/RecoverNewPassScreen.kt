package com.unieventos.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.unieventos.R
import com.unieventos.components.FormTextField

@Composable
fun RecoverNewPassScreen(
    onNavigationToLogin: () -> Unit
) {
    val context = LocalContext.current

    Scaffold { padding ->
        SignUpNewPass(
            padding = padding,
            context = context,
            onNavigationToLogin = onNavigationToLogin
        )
    }
}

@Composable
fun SignUpNewPass(
    padding: PaddingValues,
    context: Context,
    onNavigationToLogin: () -> Unit
) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConfirm by rememberSaveable { mutableStateOf("") }
    var isPasswordMatch by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FormTextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordMatch = passwordConfirm == password
            },
            supportingText = stringResource(id = R.string.password_validation),
            label = "Contraseña",
            Onvalidate = {
                password.length < 6
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isPassword = true
        )

        FormTextField(
            value = passwordConfirm,
            onValueChange = {
                passwordConfirm = it
                isPasswordMatch = passwordConfirm == password
            },
            supportingText = if (!isPasswordMatch) {
                "Las contraseñas no coinciden"
            } else {
                stringResource(id = R.string.password_validation)
            },
            label = "Confirmar Contraseña",
            Onvalidate = {
                passwordConfirm.length < 6 || passwordConfirm != password
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isPassword = true
        )

        Row {
            Button(onClick = {
                if (password == passwordConfirm) {
                    onNavigationToLogin()
                }
            }) {
                Text(text = "Crear Contraseña")
            }
        }
    }
}