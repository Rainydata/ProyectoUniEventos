package com.unieventos.screens

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.unieventos.components.FormTextField
import com.unieventos.R
import com.unieventos.model.Role
import com.unieventos.model.User
import com.unieventos.viewmodel.UsersViewModel

@Composable
fun SignUpScreen(
    onNavigationBack: () -> Unit,
    usersViewModel: UsersViewModel
) {
    val context = LocalContext.current

    Scaffold { padding ->
        SignUpForm(
            padding = padding,
            context = context,
            onNavigationBack = onNavigationBack,
            usersViewModel = usersViewModel
        )
    }
}

@Composable
fun SignUpForm(
    padding: PaddingValues,
    context: Context,
    onNavigationBack: () -> Unit,
    usersViewModel: UsersViewModel
) {
    var cedula by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
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
            value = cedula,
            onValueChange = {
                cedula = it
            },
            supportingText = stringResource(id = R.string.cedula_validation),
            label = "Cedula",
            Onvalidate = {
                cedula.length > 60
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isPassword = false
        )

        FormTextField(
            value = name,
            onValueChange = {
                name = it
            },
            supportingText = stringResource(id = R.string.Name_validation),
            label = "Nombre",
            Onvalidate = {
                name.length < 60
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isPassword = false
        )

        FormTextField(
            value = address,
            onValueChange = {
                address = it
            },
            supportingText = stringResource(id = R.string.address_validation),
            label = "Dirección",
            Onvalidate = {
                address.length < 60
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
            supportingText = stringResource(id = R.string.PhoneNumber_validation),
            label = "Número",
            Onvalidate = {
                phoneNumber.length != 10
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isPassword = false
        )

        FormTextField(
            value = email,
            onValueChange = {
                email = it
            },
            supportingText = stringResource(id = R.string.email_validation),
            label = "Correo",
            Onvalidate = {
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isPassword = false
        )

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
                if (isPasswordMatch) {
                    usersViewModel.createUser(User(
                        cedula = cedula,
                        name = name,
                        address = address,
                        phoneNumber =phoneNumber,
                        email = email,
                        password = password,
                        role = Role.CLIENT
                    ))
                    Toast.makeText(context, context.getString(R.string.userCreated), Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Crear Cuenta")
            }
        }
    }
}


