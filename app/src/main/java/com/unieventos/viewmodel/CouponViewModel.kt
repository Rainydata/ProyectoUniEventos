package com.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.unieventos.navigation.dto.CouponDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CouponViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _coupons = MutableStateFlow<List<CouponDTO>>(emptyList())
    val coupons: StateFlow<List<CouponDTO>> get() = _coupons

    fun getCoupons() {
        viewModelScope.launch {
            val snapshot = db.collection("coupons").get().await()
            val couponsList = snapshot.documents.mapNotNull { doc ->
                doc.toObject(CouponDTO::class.java)
            }
            _coupons.value = couponsList
        }
    }

    fun createCoupon(coupon: CouponDTO) {
        viewModelScope.launch {
            db.collection("coupons").add(coupon)
        }
    }

    fun updateCoupon(couponId: String, updatedCoupon: CouponDTO) {
        viewModelScope.launch {
            db.collection("coupons").document(couponId).set(updatedCoupon)
        }
    }

    fun deleteCoupon(couponId: String) {
        viewModelScope.launch {
            db.collection("coupons").document(couponId).delete()
        }
    }
}