package com.unieventos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.unieventos.model.Role
import com.unieventos.screens.EditProfileScreen
import com.unieventos.screens.HomeScreen
import com.unieventos.screens.RecoverCodeScreen
import com.unieventos.screens.RecoverNewPassScreen
import com.unieventos.screens.loginScreen
import com.unieventos.screens.SignUpScreen
import com.unieventos.screens.RecoverPassScreen
import com.unieventos.utils.SharedPreferenceUtils
import com.unieventos.viewmodel.UsersViewModel


@Composable
fun Navigation(
    usersViewModel: UsersViewModel
){
    val navController = rememberNavController()
    val context = LocalContext.current
    var starDestination: RoutScreen = RoutScreen.Login
    val sesion = SharedPreferenceUtils.getCurrentUser(context)

    if(sesion != null){
        starDestination = if(sesion.rol == Role.ADMIN){
            RoutScreen.Recover
        }else{
            RoutScreen.Home
        }
    }

    NavHost(
        navController = navController,
        startDestination = starDestination  )
    {
        composable<RoutScreen.Login>{
            loginScreen(
                onNavigationToHome = { role ->
                    val home = if(role == Role.ADMIN){
                        RoutScreen.HomeAdmin
                    }else{
                        RoutScreen.Home
                    }
                    navController.navigate(home){

                    }

                },
                onNavigationToSignUp = {
                    navController.navigate(RoutScreen.Registration)
                },
                onNavigationToRecover = {
                    navController.navigate(RoutScreen.Recover)
                },
                usersViewModel = usersViewModel
            )
        }
//        composable<RoutScreen.HomeAdmin> {
//            HomeScreen(
//                onNavigationToEditProfile = {
//                    navController.navigate(RoutScreen.HomeAdmin)
//                }
//            )
//        }
//
//        composable<RoutScreen.Home> {
//            HomeScreen(
//                onNavigationToEditProfile = {
//                    navController.navigate(RoutScreen.EditProfile)
//                }
//            )
//        }

        composable<RoutScreen.EditProfile> {
            EditProfileScreen()
        }

//        composable<RoutScreen.Registration>{
//            SignUpScreen(
//                onNavigationBack = {
//                    navController.popBackStack()
//                },
//                usersViewModel = usersViewModel
//            )
//        }

        composable<RoutScreen.Recover>{
            RecoverPassScreen(
                onNavigationToCodePass = {
                    navController.navigate(RoutScreen.RecoverCode)
                }
            )
        }
        composable<RoutScreen.RecoverCode> {
            RecoverCodeScreen(onNavigationToRecoverCofirmCode = {
                navController.navigate(RoutScreen.RecoverCodeConfirm)
            })
        }
        composable<RoutScreen.RecoverCodeConfirm> {
             RecoverNewPassScreen(onNavigationToLogin = {
                 navController.navigate(RoutScreen.Login)
             })

            }
        }
    }
