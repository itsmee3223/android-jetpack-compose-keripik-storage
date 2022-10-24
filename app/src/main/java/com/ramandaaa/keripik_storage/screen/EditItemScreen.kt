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
import com.ramandaaa.keripik_storage.viewmodel.EditDeleteViewModel

@Composable
fun EditItemScreen(navController: NavHostController, editViewModel: EditDeleteViewModel){
    var newSelectedImage by remember { mutableStateOf<Uri>(Uri.parse("")) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri ->
        newSelectedImage = uri
    }

    Scaffold(
        bottomBar = { BottomBarComponent(navController) },
        backgroundColor = BackgroundApp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp, 0.dp),
            verticalArrangement = Arrangement.Center
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageEditInput(newSelectedImage = newSelectedImage, editViewModel = editViewModel) {
                launcher.launch("image/*")
            }

            EditInputField(editViewModel = editViewModel)
            Button(editViewModel = editViewModel, newSelectedImage = newSelectedImage)
        }
    }
}

@Composable
private fun EditInputField(editViewModel: EditDeleteViewModel){
    val stockKeripik = editViewModel.stockKeripik.value
    var namaKeripik = editViewModel.namaKeripik.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
    ) {

        OutlinedTextField(
            value = namaKeripik,
            onValueChange = { namaKeripik = "" },
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
            onValueChange = { editViewModel.setStockKeripik(it) },
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
private fun Button(editViewModel: EditDeleteViewModel, newSelectedImage: Uri) {
    val contex = LocalContext.current

    Button(
        onClick = {
            if (editViewModel.namaKeripik.value.isEmpty() || editViewModel.stockKeripik.value.isEmpty()) {
                Toast.makeText(contex, "Lengkapi data product!!!", Toast.LENGTH_SHORT).show()
            }

            if (editViewModel.stateGambar.value) {
                editViewModel.setNewGambarKeripik(newSelectedImage)
            }

            editViewModel.updateKeripik(contex)
        },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
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
private fun ImageEditInput(
    newSelectedImage: Uri? = null,
    editViewModel: EditDeleteViewModel,
    onImageClick: () -> Unit,
){
    if(newSelectedImage.toString() != ""){
        editViewModel.setStateGambar(true)
        Image(
            rememberImagePainter(newSelectedImage),
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
        editViewModel.setStateGambar(false)
        Image(
            rememberImagePainter(editViewModel.gambarKeripik.value),
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
    }
}