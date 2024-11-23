package com.unieventos.screens

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.unieventos.components.FormTextField
import com.unieventos.R
import com.unieventos.model.Role
import com.unieventos.utils.RequestResult
import com.unieventos.utils.SharedPreferenceUtils
import com.unieventos.viewmodel.UsersViewModel
import kotlinx.coroutines.delay

@Composable
fun loginScreen(
    onNavigationToHome: () -> Unit,
    onNavigationToSignUp: () -> Unit,
    onNavigationToRecover: () -> Unit,
    usersViewModel: UsersViewModel
){

    val context = LocalContext.current

    Scaffold { padding ->
        LoginForm(
            padding = padding,
            onNavigationToHome = onNavigationToHome,
            onNavigationToSignUp = onNavigationToSignUp,
            onNavigationToRecover = onNavigationToRecover,
            usersViewModel = usersViewModel

        )
    }
}

@Composable
fun LoginForm(
    padding: PaddingValues,
    onNavigationToHome: () -> Unit,
    onNavigationToSignUp: () -> Unit,
    onNavigationToRecover: () -> Unit,
    usersViewModel: UsersViewModel
) {

    val authResult by usersViewModel.authResult.collectAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf(false) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Ajusta el verticalArrangement
        ) {
            // Campos de entrada
            FormTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                supportingText = stringResource(id = R.string.email_validation),
                label = "Email",
                Onvalidate = {
                    !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isPassword = false
            )
            Spacer(modifier = Modifier.height(10.dp))

            FormTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                supportingText = stringResource(id = R.string.password_validation),
                label = "Password",
                Onvalidate = {
                    password.length < 6
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isPassword = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Button(
                    enabled = email.isNotEmpty() && password.isNotEmpty() && !emailError && !passwordError,
                    onClick = {
                        usersViewModel.login(email, password)
                    }
                ) {
                    Text(text = "Iniciar Sesion")
                }
                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {


                        onNavigationToSignUp()
                    }
                ) {
                    Text(text = "Registrarse")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                        onNavigationToRecover()
                }
            ) {
                Text(text = "¿Has olvidado la contraseña?")
            }

            when (authResult) {
                is RequestResult.Loading -> {
                    CircularProgressIndicator()
                }
                is RequestResult.Success -> {
                    Text(
                        text = (authResult as RequestResult.Success).message,
                        color = MaterialTheme.colorScheme.primary
                    )
                    LaunchedEffect(Unit) {
                        delay(1200)
                        onNavigationToHome()
                        usersViewModel.resetAuthResult()
                    }
                }
                is RequestResult.Failure ->{
                    Text(
                        text = (authResult as RequestResult.Failure).messageError,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                null -> {}

            }

        }
    }
}

            


