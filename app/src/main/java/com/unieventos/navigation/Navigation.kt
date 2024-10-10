package com.unieventos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.unieventos.model.Role
import com.unieventos.screens.EditProfileScreen
import com.unieventos.screens.EventDetailScreen
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
//    var startDestination: RouteScreen = RouteScreen.Login
    var startDestination: RouteScreen = RouteScreen.Home
    val sesion = SharedPreferenceUtils.getCurrentUser(context)

    if(sesion != null){
        startDestination = if(sesion.rol == Role.ADMIN){
            RouteScreen.Recover
        }else{
            RouteScreen.Home
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination  )
    {
        composable<RouteScreen.Login>{
            loginScreen(
                onNavigationToHome = { role ->
                    val home = if(role == Role.ADMIN){
                        RouteScreen.HomeAdmin
                    }else{
                        RouteScreen.Home
                    }
                    navController.navigate(home){

                    }

                },
                onNavigationToSignUp = {
                    navController.navigate(RouteScreen.Registration)
                },
                onNavigationToRecover = {
                    navController.navigate(RouteScreen.Recover)
                },
                usersViewModel = usersViewModel
            )
        }
//        composable<RouteScreen.HomeAdmin> {
//            HomeScreen(
//                onNavigationToEditProfile = {
//                    navController.navigate(RouteScreen.HomeAdmin)
//                }
//            )
//        }

        composable<RouteScreen.Home> {
            HomeScreen(
                onNavigationToEditProfile = {
                    navController.navigate(RouteScreen.EditProfile)
                },
                onNavigationToEventDetail = { eventId ->
                    navController.navigate(RouteScreen.EventDetail(eventId))
                }
            )
        }

        composable<RouteScreen.EditProfile> {
            EditProfileScreen()
        }

        composable<RouteScreen.Registration>{
            SignUpScreen(
                onNavigationBack = {
                    navController.popBackStack()
                },
                usersViewModel = usersViewModel
            )
        }

        composable<RouteScreen.Recover>{
            RecoverPassScreen(
                onNavigationToCodePass = {
                    navController.navigate(RouteScreen.RecoverCode)
                }
            )
        }
        composable<RouteScreen.RecoverCode> {
            RecoverCodeScreen(onNavigationToRecoverCofirmCode = {
                navController.navigate(RouteScreen.RecoverCodeConfirm)
            })
        }
        composable<RouteScreen.RecoverCodeConfirm> {
             RecoverNewPassScreen(onNavigationToLogin = {
                 navController.navigate(RouteScreen.Login)
             })

            }
        composable<RouteScreen.EventDetail> {
            val args = it.toRoute<RouteScreen.EventDetail>()
           EventDetailScreen(
               eventId = args.eventId,
               onNavigateBack={
                   navController.popBackStack()
               }
           )
        }
        }
    }
