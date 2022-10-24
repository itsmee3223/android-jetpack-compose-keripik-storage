package com.ramandaaa.keripik_storage.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.ramandaaa.keripik_storage.sealed.Screens
import com.ramandaaa.keripik_storage.ui.theme.BackgroundApp
import com.ramandaaa.keripik_storage.viewmodel.LoginRegisterViewModel
import com.ramandaaa.keripik_storage.R
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController, viewModel: LoginRegisterViewModel) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(targetValue = if (startAnimation) 1f else 0f, animationSpec = tween(
        durationMillis = 3000
    )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        if(viewModel.isLoggedIn.value){
            navController.navigate(Screens.HomeScreen.route)
        } else {
            navController.navigate(Screens.LoginScreen.route)
        }
    }
    SplashLayout(alpha = alphaAnim.value)

}

@Composable
fun SplashLayout(alpha: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha = alpha)
            .background(BackgroundApp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(painter = painterResource(id = R.drawable.keripik_logo), contentDescription = "Keripik Logo")
        }
    }
}