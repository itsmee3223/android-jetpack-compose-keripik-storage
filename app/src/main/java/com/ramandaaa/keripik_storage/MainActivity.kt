package com.ramandaaa.keripik_storage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ramandaaa.keripik_storage.navigation.SetupNavGraph
import com.ramandaaa.keripik_storage.ui.theme.KeripikstorageTheme
import com.ramandaaa.keripik_storage.viewmodel.AddItemViewModel
import com.ramandaaa.keripik_storage.viewmodel.EditDeleteViewModel
import com.ramandaaa.keripik_storage.viewmodel.LoginRegisterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen()
        }
    }
}

@Composable
fun BaseScreen(
    loginViewModel: LoginRegisterViewModel = hiltViewModel(),
    addItemViewModel: AddItemViewModel = hiltViewModel(),
    keripikViewModel: EditDeleteViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    SetupNavGraph(
        navController = navController,
        loginViewModel,
        addItemViewModel,
        keripikViewModel,
    )
}