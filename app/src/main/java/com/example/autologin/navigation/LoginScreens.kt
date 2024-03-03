package com.example.autologin.navigation

enum class LoginScreens{
    DataScreen,
    LoginScreen;
    companion object{
        fun fromRoute(route: String?) : LoginScreens
        = when(route?.substringBefore("/")){
            DataScreen.name -> DataScreen
            LoginScreen.name -> LoginScreen
            else -> throw IllegalArgumentException("Route $route is not recognized.")
        }
    }
}
