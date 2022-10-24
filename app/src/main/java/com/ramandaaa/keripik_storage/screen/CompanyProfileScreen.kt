package com.ramandaaa.keripik_storage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.ramandaaa.keripik_storage.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramandaaa.keripik_storage.ui.theme.BackgroundApp

@Composable
fun CompanyProfileScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBarComponent(navController = navController) },
        backgroundColor = BackgroundApp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp, 0.dp)
        ) {
            Text(
                text = "TOKO",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Keripik Karya Mandiri",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.company_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp, 270.dp)
                    .padding(0.dp, 48.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
                ,
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Jl. Pagar Alam Gg. Pu, Segala Mider, Kec. Tj. Karang Bar., Kota Bandar Lampung, Lampung 35152, Indonesia",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Telpon: +62 813-6122-8252",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
            )
        }
    }
}