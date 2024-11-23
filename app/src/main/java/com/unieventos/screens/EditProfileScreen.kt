package com.unieventos.screens

import android.content.Context
import android.util.Patterns
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
fun EditProfileScreen() {
    val context = LocalContext.current

    Scaffold { padding ->
        EditProfileForm(
            padding = padding,
            context = context
        )
    }
}

@Composable
fun EditProfileForm(
    padding: PaddingValues,
    context: Context
) {
    var email by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FormTextField(
            value = email,
            onValueChange = {
                email = it
            },
            supportingText = stringResource(id = R.string.password_validation),
            label = "Correo",
            Onvalidate = {
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isPassword = false
        )

        FormTextField(
            value = name,
            onValueChange = {
                name = it
            },
            supportingText = stringResource(id = R.string.Name_validation) ,
            label = "Nombre",
            Onvalidate = {
                name.isEmpty()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isPassword = false
        )

        FormTextField(
            value = address,
            onValueChange = {
                address = it
            },
            supportingText =  stringResource(id = R.string.address_validation) ,
            label = "Dirección",
            Onvalidate = {
                address.isEmpty()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isPassword = false
        )

        FormTextField(
            value = phoneNumber,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    phoneNumber = it
                }
            },
            supportingText =  stringResource(id = R.string.PhoneNumber_validation) ,
            label = "Número",
            Onvalidate = {
                phoneNumber.length != 10
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isPassword = false
        )

        Row {
            Button(onClick = {  }) {
                Text(text = "Guardar Cambios")
            }
        }
    }
}