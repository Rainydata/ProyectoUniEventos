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
import com.unieventos.components.FormTextField
import com.unieventos.R
@Composable
fun RecoverCodeScreen(
    onNavigationToRecoverCofirmCode: () -> Unit
){
    val context = LocalContext.current

    Scaffold { padding ->
        CodeVerification(
            padding = padding,
            context = context,
            onNavigationToRecoverCofirmCode = onNavigationToRecoverCofirmCode
        )
    }
}
@Composable
fun CodeVerification(
    padding: PaddingValues,
    context: Context,
    onNavigationToRecoverCofirmCode: () -> Unit
){
    var Code by rememberSaveable { mutableStateOf("") }
    Column (
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        FormTextField(
            value = Code,
            onValueChange = {
                Code = it
            },
            supportingText =  stringResource(id = R.string.Code_validation),
            label = "Codigo",
            Onvalidate = {
                Code.length < 6
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isPassword = false
        )
        Row {
            Button(onClick = {
                onNavigationToRecoverCofirmCode()
            }) {
                Text(text = "Verificar")
            } }}}