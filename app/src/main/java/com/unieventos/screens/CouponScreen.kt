package com.unieventos.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.Button



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponScreen(
    onNavigationBack: () -> Unit,
    couponsViewModel: CouponsViewModel = viewModel(),
) {
    // Obtener el estado de los cupones desde el ViewModel
    val coupons by couponsViewModel.coupons.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    // Filtrar los cupones en función de la búsqueda
    val filteredCoupons = if (searchQuery.isEmpty()) {
        coupons
    } else {
        couponsViewModel.searchCoupons(searchQuery)
    }

    // Variables para el formulario de cupón
    var editingCoupon by remember { mutableStateOf<Coupon?>(null) }
    var couponName by remember { mutableStateOf("") }
    var couponDiscount by remember { mutableStateOf(0.0) }
    var couponExpiryDate by remember { mutableStateOf("") }

    // Funcion para crear o actualizar un cupón
    fun onSaveCoupon() {
        if (editingCoupon != null) {
            // Actualizar cupón existente
            val updatedCoupon = editingCoupon!!.copy(name = couponName, discount = couponDiscount, expiryDate = couponExpiryDate)
            couponsViewModel.createCoupon(updatedCoupon)
        } else {
            // Crear un nuevo cupón
            couponsViewModel.createCoupon(
                Coupon(
                    id = coupons.size + 1,
                    name = couponName,
                    discount = couponDiscount,
                    expiryDate = couponExpiryDate
                )
            )
        }
        // Limpiar el formulario
        editingCoupon = null
        couponName = ""
        couponDiscount = 0.0
        couponExpiryDate = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cupones") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Limpiar el formulario para crear un nuevo cupón
                editingCoupon = null
                couponName = ""
                couponDiscount = 0.0
                couponExpiryDate = ""
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

            // Formulario de agregar o editar cupón
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = couponName,
                    onValueChange = { couponName = it },
                    label = { Text("Nombre del Cupón") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = couponDiscount.toString(),
                    onValueChange = { couponDiscount = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text("Descuento (%)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = couponExpiryDate,
                    onValueChange = { couponExpiryDate = it },
                    label = { Text("Fecha de Vencimiento") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar el cupón
                Button(
                    onClick = { onSaveCoupon() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (editingCoupon != null) "Actualizar Cupón" else "Agregar Cupón")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de cupones
            LazyColumn {
                items(filteredCoupons) { coupon ->
                    CouponItem(coupon = coupon, onDelete = {
                        couponsViewModel.deleteCoupon(coupon)
                    }, onEdit = {
                        // Cargar los datos del cupón seleccionado en el formulario para editarlo
                        editingCoupon = coupon
                        couponName = coupon.name
                        couponDiscount = coupon.discount
                        couponExpiryDate = coupon.expiryDate
                    })
                }
            }
        }
    }
}

@Composable
fun CouponItem(coupon: Coupon, onDelete: () -> Unit, onEdit: () -> Unit) {
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
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Cupón")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar Cupón")
                }
            }
        }
    }
}

