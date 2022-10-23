package com.ramandaaa.keripik_storage.screen

import androidx.compose.foundation.Image
import com.ramandaaa.keripik_storage.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramandaaa.keripik_storage.sealed.Screens
import com.ramandaaa.keripik_storage.ui.theme.BackgroundApp
import com.ramandaaa.keripik_storage.ui.theme.ButtonPrimary
import com.ramandaaa.keripik_storage.ui.theme.ButtonSecondary
import com.ramandaaa.keripik_storage.viewmodel.LoginRegisterViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginRegisterViewModel) {
    LoginLayout(navController, viewModel)
}

@Composable
fun LoginLayout(navController: NavHostController, viewModel: LoginRegisterViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundApp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 32.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                fontSize = 24.sp,
                text = "Sign In",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = R.drawable.keripik_logo),
                contentDescription = "keripik logo",
                Modifier.size(100.dp, 100.dp)
            )
            InputLoginField(navController, viewModel)
        }
    }

}

@Composable
fun InputLoginField(navController: NavHostController, viewModel: LoginRegisterViewModel) {
    val userEmail = viewModel.userEmail.value
    val password = viewModel.password.value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
    ) {
        OutlinedTextField(
            value = userEmail,
            onValueChange = { viewModel.setUserEmail(it) },
            label = { Text("Email", color = Color.White) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .padding(24.dp, 0.dp, 24.dp, 20.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray),
        )
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.setPassword(it) },
            label = { Text("Password", color = Color.White) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .padding(24.dp, 0.dp, 24.dp, 60.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray),
            visualTransformation = PasswordVisualTransformation(),
        )
        Buttons(navController, viewModel)
    }
}

@Composable
fun Buttons(navController: NavHostController, viewModel: LoginRegisterViewModel) {
    val context = LocalContext.current
    Column {
        androidx.compose.material.Button(onClick = {
            viewModel.signInWithEmailAndPassword(context, navController)
        }, shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(
            backgroundColor = ButtonPrimary,
            contentColor = Color.White
        ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 0.dp)
                .height(50.dp),
            enabled = viewModel.isValidEmailAndPassword()
        ) {
            Text(text = "MASUK AKUN")
        }

        androidx.compose.material.Button(onClick = {
            navController.navigate(Screens.RegisterScreen.route)
        }, shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(
            backgroundColor = ButtonSecondary,
            contentColor = Color.White
        ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 20.dp)
                .height(50.dp)
        ) {
            Text(text = "DAFTAR AKUN BARU")
        }
    }
}