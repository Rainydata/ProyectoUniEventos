package com.unieventos.components

import android.util.Patterns
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.w3c.dom.Text
import java.util.regex.Pattern

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    supportingText: String,
    label: String,
    Onvalidate: (String) -> Boolean,
    keyboardOptions: KeyboardOptions,
    isPassword: Boolean,
    modifier: Modifier = Modifier
){
    var isError by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        isError = isError,
        supportingText = {
            if(isError){
                Text(text = supportingText)
            }
        },
        visualTransformation = if(isPassword){
            PasswordVisualTransformation() } else { VisualTransformation.None},
        keyboardOptions = keyboardOptions,
        label = {
            Text(text = label)
        },
        onValueChange = {
            onValueChange(it)
            isError = Onvalidate(it)
        }
    )
}