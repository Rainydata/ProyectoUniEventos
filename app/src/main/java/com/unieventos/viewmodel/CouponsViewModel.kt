package com.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import com.unieventos.model.Coupon
import com.unieventos.utils.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CouponsViewModel : ViewModel() {

    // StateFlow para mantener la lista de cupones
    private val _coupons = MutableStateFlow(emptyList<Coupon>())
    val coupons: StateFlow<List<Coupon>> = _coupons.asStateFlow()

    private val _couponCreationResult = MutableStateFlow<RequestResult?>(null)
    val couponCreationResult: StateFlow<RequestResult?> = _couponCreationResult.asStateFlow()


    init {
        _coupons.value = getCouponsList()
    }

    // Crear un nuevo cupón
    fun createCoupon(coupon: Coupon) {
        _coupons.value += coupon
    }

    // Eliminar un cupón de la lista
    fun deleteCoupon(coupon: Coupon) {
        _coupons.value -= coupon
    }

    // Buscar un cupón por su ID
    fun findCouponById(id: String): Coupon? {
        return _coupons.value.find { it.id == id }
    }

    // Buscar cupones que coincidan con el nombre
    fun searchCoupons(query: String): List<Coupon> {
        return _coupons.value.filter { it.name.contains(query, ignoreCase = true) }
    }

    fun resetCouponCreationResult(){
        _couponCreationResult.value = null

    }


    fun getCouponsList(): List<Coupon> {
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
    }
}
