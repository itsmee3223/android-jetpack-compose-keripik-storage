package com.ramandaaa.keripik_storage.sealed

sealed class Screens(val route:String) {
    object SplashScreen : Screens("splash_screen")
    object LoginScreen  : Screens("login_screen")
    object RegisterScreen : Screens("register_screen")
    object AddScreen: Screens("add")
    object HomeScreen: Screens("home")
    object ProfileScreen: Screens("profile")
    object EditItemScreen: Screens("edit")
}