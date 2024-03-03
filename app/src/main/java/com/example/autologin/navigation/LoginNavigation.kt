package com.example.autologin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autologin.screens.LoginScreen
import com.example.autologin.screens.DataScreen


@Composable
fun LoginNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginScreens.LoginScreen.name
    ) {
        composable(LoginScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(LoginScreens.DataScreen.name) {
            DataScreen(navController = navController)
        }
    }
}