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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.unieventos.components.FormTextField
import com.unieventos.R
import com.unieventos.model.Coupon
import com.unieventos.model.Role
import com.unieventos.model.User
import com.unieventos.utils.RequestResult
import com.unieventos.viewmodel.CouponsViewModel
import com.unieventos.viewmodel.UsersViewModel
import kotlinx.coroutines.delay

@Composable
fun CreateCouponScreen(
    onNavigationBack: () -> Unit,
    couponsViewModel: CouponsViewModel
) {
    val context = LocalContext.current

    var name by rememberSaveable { mutableStateOf("") }
    var discount by rememberSaveable { mutableStateOf("") }
    var expiryDate by rememberSaveable { mutableStateOf("") }

    var isDiscountValid by rememberSaveable { mutableStateOf(true) }
    var isFormValid by rememberSaveable {
        mutableStateOf(false)
    }

    val couponCreationResult by couponsViewModel.couponCreationResult.collectAsState()

    LaunchedEffect(name, discount, expiryDate, isDiscountValid) {
        isFormValid = name.isNotBlank() && isDiscountValid && expiryDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Campo para el nombre
            FormTextField(
                value = name,
                onValueChange = { name = it },
                supportingText = "Ingrese el nombre del cupón",
                label = "Nombre",
                Onvalidate = { name.isBlank() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isPassword = false
            )

            // Campo para el descuento
            FormTextField(
                value = discount,
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == '.' }) {
                        discount = it
                        isDiscountValid = it.toDoubleOrNull()?.let { value -> value in 0.0..100.0 } ?: false
                    }
                },
                supportingText = if (!isDiscountValid) {
                    "El descuento debe estar entre 0 y 100"
                } else {
                    "Ingrese el descuento (%)"
                },
                label = "Descuento",
                Onvalidate = { !isDiscountValid },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isPassword = false
            )

            // Campo para la fecha de expiración
            FormTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                supportingText = "Ingrese la fecha de expiración (YYYY-MM-DD)",
                label = "Fecha de Expiración",
                Onvalidate = {
                    !expiryDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isPassword = false
            )

            // Botón de creación
            Button(
                onClick = {
                    couponsViewModel.createCoupon(
                        Coupon(
                            id = "", // Se generará automáticamente
                            name = name,
                            discount = discount.toDouble(),
                            expiryDate = expiryDate
                        )
                    )
                },
                enabled = isFormValid
            ) {
                Text(text = "Crear Cupón")
            }

            // Estado de creación del cupón
            when (couponCreationResult) {
                is RequestResult.Loading -> {
                    CircularProgressIndicator()
                }
                is RequestResult.Success -> {
                    Text(
                        text = (couponCreationResult as RequestResult.Success).message,
                        color = MaterialTheme.colorScheme.primary
                    )
                    LaunchedEffect(Unit) {
                        delay(1200)
                        onNavigationBack()
                        couponsViewModel.resetCouponCreationResult()
                    }
                }
                is RequestResult.Failure -> {
                    Text(
                        text = (couponCreationResult as RequestResult.Failure).messageError,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                null -> {}
            }
        }
    }
}
