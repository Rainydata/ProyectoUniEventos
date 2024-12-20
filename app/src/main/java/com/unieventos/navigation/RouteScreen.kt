package com.unieventos.navigation
import kotlinx.serialization.Serializable
sealed class RouteScreen {

    @Serializable
    data object  Login: RouteScreen()
    @Serializable
    data object  Registration: RouteScreen()
    @Serializable
    data object  Recover: RouteScreen()
    @Serializable
    data object  Home: RouteScreen()
    @Serializable
    data object  RecoverCode: RouteScreen()
    @Serializable
    data object  RecoverCodeConfirm: RouteScreen()
    @Serializable
    data object  HomeAdmin: RouteScreen()
    @Serializable
    data object  EditProfile: RouteScreen()
    @Serializable
    data object  CouponScreen: RouteScreen()
    @Serializable
    data object  CreateCoupon: RouteScreen()
    @Serializable
    data object  CreateEvent: RouteScreen()


    @Serializable
    data class EventDetail(val eventId: String) : RouteScreen()

}