package com.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unieventos.model.Coupon
import com.unieventos.model.Event
import com.unieventos.utils.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CouponsViewModel : ViewModel() {

    val db = Firebase.firestore

    // StateFlow para mantener la lista de cupones
    private val _coupons = MutableStateFlow(emptyList<Coupon>())
    val coupons: StateFlow<List<Coupon>> = _coupons.asStateFlow()

    private val _couponCreationResult = MutableStateFlow<RequestResult?>(null)
    val couponCreationResult: StateFlow<RequestResult?> = _couponCreationResult.asStateFlow()


    init {
    loadCoupons()
    }

    // Crear un nuevo cupón
    fun createCoupon(coupon: Coupon) {

        viewModelScope.launch {
            db.collection("coupons")
                .add(coupon)
                .await()
            loadCoupons()

            _coupons.value = getCouponsList()

        }
    }

    fun loadCoupons(){
        viewModelScope.launch {
            _coupons.value = getCouponsList()
        }
    }

    // Eliminar un cupón de la lista
    fun deleteCoupon(couponId: String) {
        viewModelScope.launch{
            db.collection("coupons")
                .document(couponId)
                .delete()
                .await()

            _coupons.value = getCouponsList()

        }
    }

    // Buscar un cupón por su ID
    suspend fun findCouponById(id: String): Coupon? {
        val snapshot = db.collection("coupons")
            .document(id)
            .get()
            .await()

        val coupon = snapshot.toObject(Coupon::class.java)
        coupon?.id = snapshot.id
        return coupon
    }

    // Buscar cupones que coincidan con el nombre
    fun searchCoupons(query: String): List<Coupon> {
        return listOf()
    }

    fun resetCouponCreationResult(){
        _couponCreationResult.value = null

    }

    private suspend fun getCouponsList(): List<Coupon> {
        val snapshot = db.collection("coupons")
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            val coupon = it.toObject(Coupon::class.java)
            requireNotNull(coupon)
            coupon.id = it.id
            coupon
        }
    }


 /*   fun getCouponsList(): List<Coupon> {
        return listOf(
            Coupon(
                id = "1",
                name = "Descuento Primavera",
                discount = 20.0,
                expiryDate = "2024-12-31"
            ),
            Coupon(
                id = "2",
                name = "Black Friday",
                discount = 50.0,
                expiryDate = "2024-11-30"
            ),
            Coupon(
                id = "3",
                name = "Cyber Monday",
                discount = 30.0,
                expiryDate = "2024-11-25"
            ),
            Coupon(
                id = "4",
                name = "Navidad",
                discount = 15.0,
                expiryDate = "2024-12-24"
            )
        )
    }*/
}
