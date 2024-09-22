package com.unieventos.navigation
import kotlinx.serialization.Serializable
sealed class RoutScreen {

    @Serializable
    data object  Login: RoutScreen()
    @Serializable
    data object  Registration: RoutScreen()
    @Serializable
    data object  Recover: RoutScreen()
    @Serializable
    data object  Home: RoutScreen()
    @Serializable
    data object  RecoverCode: RoutScreen()
    @Serializable
    data object  RecoverCodeConfirm: RoutScreen()

    @Serializable
    data object  EditProfile: RoutScreen()

}