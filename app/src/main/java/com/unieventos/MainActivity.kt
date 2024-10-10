package com.unieventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.unieventos.navigation.Navigation
import com.unieventos.ui.theme.ProyectoUniEventosTheme
import com.unieventos.viewmodel.EventsViewModel
import com.unieventos.viewmodel.UsersViewModel

class MainActivity : ComponentActivity() {

    private val usersViewModel: UsersViewModel by viewModels()
    private val eventsViewModel: EventsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoUniEventosTheme {
                Navigation(
                    usersViewModel = usersViewModel,
                    eventsViewModel = eventsViewModel

                )
            }
        }
    }
}