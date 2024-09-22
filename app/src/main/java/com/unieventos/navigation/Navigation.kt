package com.unieventos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.unieventos.screens.EditProfileScreen
import com.unieventos.screens.HomeScreen
import com.unieventos.screens.RecoverCodeScreen
import com.unieventos.screens.RecoverNewPassScreen
import com.unieventos.screens.loginScreen
import com.unieventos.screens.SignUpScreen
import com.unieventos.screens.RecoverPassScreen


@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RoutScreen.Login  )
    {
        composable<RoutScreen.Login>{
            loginScreen(
                onNavigationToHome = {
                    navController.navigate(RoutScreen.Home)
                },
                onNavigationToSignUp = {
                    navController.navigate(RoutScreen.Registration)
                },
                onNavigationToRecover = {
                    navController.navigate(RoutScreen.Recover)
                }
            )
        }

        composable<RoutScreen.Home> {
            HomeScreen(
                onNavigationToEditProfile = {
                    navController.navigate(RoutScreen.EditProfile)
                }
            )
        }

        composable<RoutScreen.EditProfile> {
            EditProfileScreen()
        }

        composable<RoutScreen.Registration>{
            SignUpScreen()
        }

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
