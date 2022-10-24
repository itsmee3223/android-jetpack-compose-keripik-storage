package com.ramandaaa.keripik_storage.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.ramandaaa.keripik_storage.ui.theme.BackgroundApp
import com.ramandaaa.keripik_storage.ui.theme.ButtonPrimary
import com.ramandaaa.keripik_storage.viewmodel.AddItemViewModel

@Composable
fun AddItemScreen(navController: NavHostController, viewModel: AddItemViewModel){
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri ->
        selectedImage = uri
    }
    Scaffold(
        bottomBar = { BottomBarComponent(navController) },
        backgroundColor = BackgroundApp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageInput(selectedImage) {
                launcher.launch("image/*")
            }
            InputField(viewModel)
            Button(viewModel, selectedImage)
        }
    }
}

@Composable
private fun InputField(viewModel: AddItemViewModel){
    val namaKeripik = viewModel.namaKeripik.value
    val stockKeripik = viewModel.stockKeripik.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
    ) {

        OutlinedTextField(
            value = namaKeripik,
            onValueChange = { viewModel.setNamaKeripik(it) },
            label = { Text("Nama Barang", color = Color.White) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .padding(24.dp, 0.dp, 24.dp, 20.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            ),
        )
        OutlinedTextField(
            value = stockKeripik,
            onValueChange = { viewModel.setStockKeripik(it) },
            label = { Text("Stok Barang", color = Color.White) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .padding(24.dp, 0.dp, 24.dp, 60.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
private fun Button(viewModel: AddItemViewModel, selectedImage: Uri?) {
    val contex = LocalContext.current
    Button(
        onClick = {
            if(viewModel.namaKeripik.value.isEmpty() || viewModel.stockKeripik.value.isEmpty() || selectedImage == null ) {
                Toast.makeText(contex, "Lengkapi data product!!!", Toast.LENGTH_SHORT).show()
            }
            if (selectedImage != null) {
                viewModel.createKeripik(contex, selectedImage)
            }
        }, shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(
            backgroundColor = ButtonPrimary,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp)
            .height(50.dp),
    ) {
        Text(text = "OKE")
    }
}

@Composable
private fun ImageInput(
    selectedImage: Uri? = null,
    onImageClick: () -> Unit,
){
    if(selectedImage != null){
        Image(
            painter = rememberImagePainter(selectedImage),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxSize()
                .clickable {
                    onImageClick()
                }
        )
    } else {
        OutlinedButton(onClick = { onImageClick() }) {
            Text(text = "Pilih Gambar")
        }
    }
}