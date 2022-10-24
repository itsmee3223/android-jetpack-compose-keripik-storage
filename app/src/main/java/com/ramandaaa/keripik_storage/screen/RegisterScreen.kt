package com.ramandaaa.keripik_storage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.ramandaaa.keripik_storage.R
import com.ramandaaa.keripik_storage.ui.theme.BackgroundApp
import com.ramandaaa.keripik_storage.ui.theme.ButtonPrimary
import com.ramandaaa.keripik_storage.viewmodel.LoginRegisterViewModel

@Composable
fun RegisterScreen(viewModel: LoginRegisterViewModel, navController: NavHostController) {
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
                text = "Sign Up",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = R.drawable.keripik_logo),
                contentDescription = "logo keripik",
                Modifier.size(100.dp, 100.dp))
            InputFieldRegister(viewModel, navController)
        }
    }
}

@Composable
fun InputFieldRegister(viewModel: LoginRegisterViewModel, navController: NavHostController) {
    val userEmail = viewModel.userEmail.value
    val password = viewModel.password.value
    val confirmPassword = viewModel.confirmpassword.value

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
                .padding(24.dp, 0.dp, 24.dp, 20.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray),
            visualTransformation = PasswordVisualTransformation(),
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { viewModel.setConfirmPassword(it) },
            label = { Text("Confirm Password", color = Color.White) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .padding(24.dp, 0.dp, 24.dp, 20.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray),
            visualTransformation = PasswordVisualTransformation(),
        )
        RegisterButton(viewModel = viewModel, navController = navController)
    }
}

@Composable
private fun RegisterButton(viewModel: LoginRegisterViewModel, navController: NavHostController) {
    val context = LocalContext.current

    Button(
        onClick = {
            viewModel.createUserWithEmailAndPassword(context, navController)
        },
        shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(
            backgroundColor = ButtonPrimary,
            contentColor = Color.White
        ),
        enabled = viewModel.isValidEmailAndPassword(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 20.dp)
            .height(50.dp)
    ) {
        Text(text = "DAFTAR")
    }
}