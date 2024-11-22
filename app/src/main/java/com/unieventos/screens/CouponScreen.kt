package com.unieventos.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unieventos.model.Coupon
import com.unieventos.viewmodel.CouponsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponScreen(couponsViewModel: CouponsViewModel = viewModel()) {
    // Obtener el estado de los cupones desde el ViewModel
    val coupons by couponsViewModel.coupons.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    // Filtrar los cupones en función de la búsqueda
    val filteredCoupons = if (searchQuery.isEmpty()) {
        coupons
    } else {
        couponsViewModel.searchCoupons(searchQuery)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cupones") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Crear un nuevo cupón al presionar el botón
                couponsViewModel.createCoupon(
                    Coupon(
                        id = coupons.size + 1,
                        name = "Nuevo Cupón",
                        discount = 10.0,
                        expiryDate = "2025-12-31"
                    )
                )
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Cupón")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Campo de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query -> searchQuery = query },
                label = { Text("Buscar Cupones") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de cupones
            LazyColumn {
                items(filteredCoupons) { coupon ->
                    CouponItem(coupon = coupon, onDelete = {
                        couponsViewModel.deleteCoupon(coupon)
                    })
                }
            }
        }
    }
}

@Composable
fun CouponItem(coupon: Coupon, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Nombre: ${coupon.name}")
                Text(text = "Descuento: ${coupon.discount}%")
                Text(text = "Vencimiento: ${coupon.expiryDate}")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Cupón")
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun CouponScreenPreview() {
    CouponScreen()
}
