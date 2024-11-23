package com.unieventos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.unieventos.model.Role
import com.unieventos.screens.EditProfileScreen
import com.unieventos.screens.EventDetailScreen
import com.unieventos.screens.HomeAdmin
import com.unieventos.screens.HomeScreen
import com.unieventos.screens.RecoverCodeScreen
import com.unieventos.screens.RecoverNewPassScreen
import com.unieventos.screens.loginScreen
import com.unieventos.screens.SignUpScreen
import com.unieventos.screens.RecoverPassScreen
import com.unieventos.ui.screen.CouponScreen
import com.unieventos.utils.SharedPreferenceUtils
import com.unieventos.viewmodel.EventsViewModel
import com.unieventos.viewmodel.UsersViewModel


@Composable
fun Navigation(
    usersViewModel: UsersViewModel,
    eventsViewModel: EventsViewModel
) {

    val currentUser by usersViewModel.currentUser.collectAsState()
    val navController = rememberNavController()
    val context = LocalContext.current
    var startDestination: RouteScreen = RouteScreen.Login

    // Verificar si existe una sesión activa
    val session = SharedPreferenceUtils.getCurrentUser(context)
    if (session != null) {
        startDestination = if (session.rol == Role.ADMIN) {
            RouteScreen.HomeAdmin // Inicio en HomeAdmin si es ADMIN
        } else {
            RouteScreen.Home
        }
    }

    // Configuración de navegación
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Pantalla de Login
        composable<RouteScreen.Login> {
            loginScreen(
                onNavigationToHome = {

                    val user = currentUser

                    if(user!= null){
                        val role = user.role
                        SharedPreferenceUtils.savePreference(context, user.id, user.role)

                        val homeDestination = if (role == Role.ADMIN) {
                            RouteScreen.HomeAdmin
                        } else {
                            RouteScreen.Home
                        }
                        navController.navigate(homeDestination) {
                            popUpTo(RouteScreen.Login) { inclusive = true } // Elimina la pantalla de Login de la pila
                        }
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

//         Pantalla de HomeAdmin
        composable<RouteScreen.HomeAdmin> {
            HomeAdmin(
                onNavigationToEditProfile = {
                    navController.navigate(RouteScreen.EditProfile)
                },
                onNavigationToCreateCoupon = {
                    navController.navigate(RouteScreen.CreateCoupon)
                },
                onNavigationToEventDetail = { eventId ->
                    navController.navigate(RouteScreen.EventDetail(eventId))
                },
                onNavigationToCreateEvent = {
                    navController.navigate(RouteScreen.CreateEvent)
                },
                eventsViewModel = eventsViewModel,
                navController = navController
            )
        }


        // Pantalla de Home (para usuarios normales)
        composable<RouteScreen.Home> {
            HomeScreen(
                onNavigationToEditProfile = {
                    navController.navigate(RouteScreen.EditProfile)
                },
                onNavigationToEventDetail = { eventId ->
                    navController.navigate(RouteScreen.EventDetail(eventId))
                },
                eventsViewModel = eventsViewModel
            )
        }

        // Pantalla de Edición de Perfil
        composable<RouteScreen.EditProfile> {
            EditProfileScreen()
        }

        // Pantalla de Registro
        composable<RouteScreen.Registration> {
            SignUpScreen(
                onNavigationBack = {
                    navController.popBackStack()
                },
                usersViewModel = usersViewModel
            )
        }

        // Pantalla de Recuperar Contraseña
        composable<RouteScreen.Recover> {
            RecoverPassScreen(
                onNavigationToCodePass = {
                    navController.navigate(RouteScreen.RecoverCode)
                }
            )
        }

        // Pantallas adicionales para recuperación de contraseña
        composable<RouteScreen.RecoverCode> {
            RecoverCodeScreen(onNavigationToRecoverCofirmCode = {
                navController.navigate(RouteScreen.RecoverCodeConfirm)
            })
        }

        composable<RouteScreen.RecoverCodeConfirm> {
            RecoverNewPassScreen(
                onNavigationToLogin = {
                    navController.navigate(RouteScreen.Login)
                }
            )
        }

        // Detalle del evento
        composable<RouteScreen.EventDetail> {
            val args = it.toRoute<RouteScreen.EventDetail>()
            EventDetailScreen(
                eventId = args.eventId,
                eventsViewModel = eventsViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

//         Pantalla para crear cupones
        composable<RouteScreen.CreateCoupon> {
            CouponScreen(
                onNavigationBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

