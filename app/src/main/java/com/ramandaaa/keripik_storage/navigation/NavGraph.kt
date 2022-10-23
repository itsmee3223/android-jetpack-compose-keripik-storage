package com.ramandaaa.keripik_storage.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ramandaaa.keripik_storage.viewmodel.AddItemViewModel
import com.ramandaaa.keripik_storage.viewmodel.EditDeleteViewModel
import com.ramandaaa.keripik_storage.viewmodel.LoginRegisterViewModel
import com.ramandaaa.keripik_storage.screen.*
import com.ramandaaa.keripik_storage.sealed.Screens

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    userViewModel: LoginRegisterViewModel,
    addItemViewModel: AddItemViewModel,
    editDeleteViewModel: EditDeleteViewModel,
) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.SplashScreen.route) {
            AnimatedSplashScreen(navController, userViewModel)
        }
        composable(route = Screens.RegisterScreen.route) {
            RegisterScreen(viewModel = userViewModel, navController = navController)
        }
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navController, viewModel = userViewModel)
        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController, editDeleteViewModel = editDeleteViewModel)
        }
        composable(route = Screens.ProfileScreen.route) {
            CompanyProfileScreen(navController)
        }
        composable(route = Screens.AddScreen.route) {
            AddItemScreen(navController, viewModel = addItemViewModel)
        }
        composable(route = Screens.EditItemScreen.route) {
            EditItemScreen(navController = navController, editViewModel = editDeleteViewModel)
        }
    }
}