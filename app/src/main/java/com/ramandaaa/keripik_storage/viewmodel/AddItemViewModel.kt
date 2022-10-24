package com.ramandaaa.keripik_storage.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ramandaaa.keripik_storage.model.Keripik
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(): ViewModel() {
    private val _namaKeripik = mutableStateOf("")
    val namaKeripik: State<String> = _namaKeripik

    private val _stockKeripik = mutableStateOf("")
    val stockKeripik: State<String> = _stockKeripik


    private val keripik = Keripik()

    fun setNamaKeripik(namaKeripik: String) {
        _namaKeripik.value = namaKeripik
    }

    fun setStockKeripik(stockKeripik: String) {
        _stockKeripik.value = stockKeripik
    }

    fun createKeripik(context: Context, gambarUri: Uri) = viewModelScope.launch {
        val TAG = "UploadActivity"
        val myRef = Firebase.database.reference
        val storage = Firebase.storage
        val storageRef = storage.reference

        val riversRef = storageRef.child("images/${gambarUri.lastPathSegment}")
        val uploadTask = riversRef.putFile(Uri.parse(gambarUri.toString()))

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Log.i(TAG, "Upload gagal: $it")
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
            Log.i(TAG, "Berhasil upload")
        }

        uploadTask
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                riversRef.downloadUrl
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    keripik.gambarKeripik = task.result.toString()
                    keripik.namaKeripik = namaKeripik.value
                    keripik.stockKeripik = stockKeripik.value

                    myRef.child("keripik").child(namaKeripik.value).setValue(keripik)
                        .addOnCompleteListener{
                            Toast.makeText(context,"Berhasil...", Toast.LENGTH_SHORT).show()
                        }
                        .addOnCanceledListener {
                            Toast.makeText(context,"Gagal dibuat coba lagi nanti", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // Handle failures
                    // ...
                    Log.i(TAG, "error: $task")
                }
            }
    }
}