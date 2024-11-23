package com.unieventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.unieventos.navigation.NavGraph
import com.unieventos.ui.theme.ProyectoUniEventosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoUniEventosTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
