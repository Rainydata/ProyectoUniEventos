package com.unieventos.model

import com.google.firebase.firestore.FirebaseFirestore
import com.unieventos.dto.CouponDTO

object CouponModel {
    private val db = FirebaseFirestore.getInstance()

    fun getCoupons(onComplete: (List<CouponDTO>) -> Unit) {
        db.collection("coupons").get().addOnSuccessListener { snapshot ->
            val coupons = snapshot.toObjects(CouponDTO::class.java)
            onComplete(coupons)
        }
    }

    fun createCoupon(coupon: CouponDTO, onComplete: (Boolean, String?) -> Unit) {
        db.collection("coupons").add(coupon).addOnCompleteListener { task ->
            onComplete(task.isSuccessful, task.exception?.message)
        }
    }

    fun updateCoupon(coupon: CouponDTO, onComplete: (Boolean, String?) -> Unit) {
        db.collection("coupons").document(coupon.id).set(coupon).addOnCompleteListener { task ->
            onComplete(task.isSuccessful, task.exception?.message)
        }
    }

    fun deleteCoupon(couponId: String, onComplete: (Boolean, String?) -> Unit) {
        db.collection("coupons").document(couponId).delete().addOnCompleteListener { task ->
            onComplete(task.isSuccessful, task.exception?.message)
        }
    }
}

