package com.unieventos.screens

import android.content.Context
import android.os.RecoverySystem
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
fun RecoverPassScreen(
    onNavigationToCodePass: () -> Unit
){
    val context = LocalContext.current

    Scaffold { padding ->
        RecoverySend(
            padding = padding,
            context = context,
            onNavigationToCodePass = onNavigationToCodePass
        )
    }
}
@Composable
fun RecoverySend(
    padding: PaddingValues,
    context: Context,
    onNavigationToCodePass: () -> Unit
){
    var Email by rememberSaveable { mutableStateOf("") }
    Column (
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        FormTextField(
            value = Email,
            onValueChange = {
                Email = it
            },
            supportingText = stringResource(id = R.string.email_validation),
            label = "Correo",
            Onvalidate = {
                !Patterns.EMAIL_ADDRESS.matcher(Email).matches()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isPassword = false
        )
        Row {
            Button(onClick = {
                onNavigationToCodePass()
            }) {
                Text(text = "Enviar")
            }
        }
}}