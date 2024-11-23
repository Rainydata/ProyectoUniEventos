package com.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unieventos.model.Coupon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CouponsViewModel : ViewModel() {

    private val db = Firebase.firestore

    // Estado para la lista de cupones
    private val _coupons = MutableStateFlow<List<Coupon>>(emptyList())
    val coupons: StateFlow<List<Coupon>> = _coupons.asStateFlow()

    init {
        fetchCoupons()
    }

    // Función para obtener los cupones desde Firestore
    private fun fetchCoupons() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("coupons").get().await()
                _coupons.value = snapshot.documents.mapNotNull { it.toObject(Coupon::class.java) }
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }

    // Crear un nuevo cupón y guardarlo en Firestore
    fun createCoupon(coupon: Coupon) {
        viewModelScope.launch {
            try {
                val newCouponRef = db.collection("coupons").document()
                coupon.id = newCouponRef.id.toInt()  // Si deseas usar un ID autogenerado
                newCouponRef.set(coupon).await()
                fetchCoupons()  // Recargar la lista de cupones después de agregar uno nuevo
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }

    // Eliminar un cupón de Firestore
    fun deleteCoupon(coupon: Coupon) {
        viewModelScope.launch {
            try {
                val couponRef = db.collection("coupons").document(coupon.id.toString())
                couponRef.delete().await()
                fetchCoupons()  // Recargar la lista después de eliminar un cupón
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }

    // Buscar cupones por nombre
    fun searchCoupons(query: String): List<Coupon> {
        return _coupons.value.filter { it.name.contains(query, ignoreCase = true) }
    }
}
