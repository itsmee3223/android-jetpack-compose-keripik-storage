package com.ramandaaa.keripik_storage.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.ramandaaa.keripik_storage.model.Keripik
import com.ramandaaa.keripik_storage.sealed.DataState
import com.ramandaaa.keripik_storage.sealed.Screens
import com.ramandaaa.keripik_storage.ui.theme.BackgroundApp
import com.ramandaaa.keripik_storage.ui.theme.ButtonPrimary
import com.ramandaaa.keripik_storage.ui.theme.ButtonSecondary
import com.ramandaaa.keripik_storage.viewmodel.EditDeleteViewModel

@Composable
fun HomeScreen (navController: NavHostController, editDeleteViewModel: EditDeleteViewModel) {
    Scaffold(
        bottomBar ={ BottomBarComponent(navController) },
        backgroundColor = BackgroundApp
    ) {
        TopLayout(editDeleteViewModel = editDeleteViewModel, navController)
    }
}

@Composable
private fun TopLayout(editDeleteViewModel: EditDeleteViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp, 24.dp),
    ) {
        Text(text = "List Produk", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 14.dp))
        ItemLayout(editDeleteViewModel = editDeleteViewModel, navController = navController)
    }
}

@Composable
private fun ItemLayout(editDeleteViewModel: EditDeleteViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp),
    ) {
        SetData(editDeleteViewModel = editDeleteViewModel, navController = navController)
    }
}

@Composable
private fun CardKeripik(navController: NavHostController, keripik: Keripik, editDeleteViewModel: EditDeleteViewModel) {
    var dialogState by remember {
        mutableStateOf(false)
    }

    var idKeripik by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            rememberImagePainter(keripik.gambarKeripik),
            contentDescription = "${keripik.namaKeripik}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxSize()
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Text(text = "${keripik.namaKeripik}", fontSize = 18.sp, color = Color.White)
            Text(text = "Stock: ${keripik.stockKeripik} KG", fontSize = 14.sp, color = Color.White)
            Row(
                modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp)
            ) {
                Button(
                    onClick = {
                        editDeleteViewModel.setNamaKeripik(keripik.namaKeripik.toString())
                        editDeleteViewModel.setStockKeripik(keripik.stockKeripik.toString())
                        editDeleteViewModel.setGambarKeripik(keripik.gambarKeripik.toString())
                        navController.navigate(Screens.EditItemScreen.route)
                    },
                    Modifier
                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(ButtonSecondary)
                ) {
                    Text(text = "Edit", color = Color.White, fontSize = 12.sp)
                }
                Button(
                    onClick = {
                        dialogState = true
                        idKeripik = keripik.namaKeripik.toString()
                    },
                    colors = ButtonDefaults.buttonColors(ButtonPrimary),
                ) {
                    Text(text = "Hapus", color = Color.White, fontSize = 12.sp)
                }

                ConfirmDelete(
                    dialogState = dialogState,
                    editDeleteViewModel = editDeleteViewModel,
                    id = idKeripik,
                    context = context
                ) {
                    dialogState = !it
                }
            }
        }
    }

}



@Composable
private fun ShowLazyList(keripiks: MutableList<Keripik>, editDeleteViewModel: EditDeleteViewModel, navController: NavHostController) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(items = keripiks) { keripik ->
            CardKeripik(keripik = keripik, editDeleteViewModel = editDeleteViewModel, navController = navController)
        }
    }
}


@Composable
fun SetData(editDeleteViewModel: EditDeleteViewModel, navController: NavHostController) {
    when(val result = editDeleteViewModel.response.value) {
        is DataState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is DataState.Success -> {
            ShowLazyList(keripiks = result.data, editDeleteViewModel = editDeleteViewModel, navController = navController)
        }
        is DataState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = result.message)
            }
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "error fetching data")
            }
        }
    }
}

@Composable
private fun ConfirmDelete(
    dialogState: Boolean,
    editDeleteViewModel: EditDeleteViewModel,
    id: String,
    context: Context,
    onDismiss: (dialogState: Boolean) -> Unit,
) {
    if(dialogState){
        AlertDialog(
            onDismissRequest = { onDismiss(dialogState) },
            confirmButton = {
                TextButton(onClick = {
                    editDeleteViewModel.deleteKeripik(id, context)
                    onDismiss(dialogState)
                })
                { Text(text = "OK") }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss(dialogState) })
                { Text(text = "Cancel") }
            },
            title = { Text(text = "Konfirmasi Hapus") },
            text = { Text(text = "Apakah anda ingin menghapus produk ?") }
        )
    }

}