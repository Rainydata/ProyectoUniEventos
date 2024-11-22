//package com.unieventos.ui.screen
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.unieventos.model.Coupon
//import com.unieventos.viewmodel.CouponsViewModel
//
//@Composable
//fun CouponScreen(couponsViewModel: CouponsViewModel = hiltViewModel()) {
//    // Obtener el estado de los cupones y el mensaje de error desde el ViewModel
//    val coupons by couponsViewModel.coupons.collectAsState()
//    val searchQuery by remember { mutableStateOf("") }
//    val filteredCoupons = remember(searchQuery) {
//        if (searchQuery.isEmpty()) coupons else coupons.filter {
//            it.name.contains(searchQuery, ignoreCase = true)
//        }
//    }
//
//    // Contenedor principal con una columna
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Cupones") })
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                // Acción de crear cupón (puedes agregar una pantalla de crear cupón)
//                couponsViewModel.createCoupon(
//                    Coupon(
//                        id = coupons.size + 1,
//                        name = "Nuevo Cupón",
//                        discount = 10.0,
//                        expiryDate = "2025-12-31"
//                    )
//                )
//            }) {
//                Icon(Icons.Default.Add, contentDescription = "Agregar Cupón")
//            }
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            // Campo de búsqueda
//            OutlinedTextField(
//                value = searchQuery,
//                onValueChange = { query -> searchQuery = query },
//                label = { Text("Buscar Cupones") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Lista de cupones
//            LazyColumn {
//                items(filteredCoupons) { coupon ->
//                    CouponItem(coupon = coupon, onDelete = {
//                        couponsViewModel.deleteCoupon(coupon)
//                    })
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun CouponItem(coupon: Coupon, onDelete: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        elevation = 4.dp
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column {
//                Text(text = coupon.name, style = MaterialTheme.typography.h6)
//                Text(text = "Descuento: ${coupon.discount}%", style = MaterialTheme.typography.body2)
//                Text(text = "Vencimiento: ${coupon.expiryDate}", style = MaterialTheme.typography.body2)
//            }
//            IconButton(onClick = onDelete) {
//                Icon(Icons.Default.Delete, contentDescription = "Eliminar Cupón")
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CouponScreenPreview() {
//    CouponScreen()
//}
