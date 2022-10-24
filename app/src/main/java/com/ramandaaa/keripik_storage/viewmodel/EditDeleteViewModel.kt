package com.ramandaaa.keripik_storage.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ramandaaa.keripik_storage.model.Keripik
import com.ramandaaa.keripik_storage.sealed.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditDeleteViewModel @Inject constructor(): ViewModel() {
    private val _namaKeripik = mutableStateOf("")
    val namaKeripik: State<String> = _namaKeripik

    private val _stockKeripik = mutableStateOf("")
    val stockKeripik: State<String> = _stockKeripik

    private val _gambarKeripik = mutableStateOf("")
    val gambarKeripik: State<String> = _gambarKeripik

    private val _newGambarKeripik = mutableStateOf<Uri?>(null)
    val newGambarKeripik: MutableState<Uri?> = _newGambarKeripik

    private val _stateGambar = mutableStateOf(false)
    val stateGambar: MutableState<Boolean> = _stateGambar

    fun setNamaKeripik(namaKeripik: String) {
        _namaKeripik.value = namaKeripik
    }

    fun setStockKeripik(stockKeripik: String) {
        _stockKeripik.value = stockKeripik
    }

    fun setGambarKeripik(gambar: String) {
        _gambarKeripik.value = gambar
    }

    fun setStateGambar(StategambarOke: Boolean) {
        _stateGambar.value = StategambarOke
    }

    fun setNewGambarKeripik(newGambar: Uri) {
        _newGambarKeripik.value = newGambar
    }


    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    init {
        fetchDataFromFireBase()
    }

    private fun fetchDataFromFireBase() {
        val TAG = "EditDeleteActivity"
        val tempList = mutableListOf<Keripik>()
        response.value = DataState.Loading
        FirebaseDatabase.getInstance().getReference("keripik")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    tempList.clear()
                    for(DataSnap in snapshot.children) {
                        val keripikItem = DataSnap.getValue(Keripik::class.java)
                        tempList.add(keripikItem!!)
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i(TAG, "error: $error")
                }

            })
    }

    fun deleteKeripik(namaKeripik: String, context: Context) {
        val myRef = FirebaseDatabase.getInstance().getReference("keripik")

        myRef.child(namaKeripik).removeValue()
            .addOnCanceledListener {
                Toast.makeText(context,"Gagal dihapus", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener{
                Toast.makeText(context,"Berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
    }

    fun updateKeripik(context: Context) {
        val TAG = "EditActivity"
        val myRef = Firebase.database.reference
        val storage = Firebase.storage
        val storageRef = storage.reference

        val riversRef = storageRef.child("images/${newGambarKeripik.value?.lastPathSegment}")

        if(stateGambar.value) {
            val uploadTask = riversRef.putFile(Uri.parse(newGambarKeripik.value.toString()))
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

                        val keripik = Keripik(
                            namaKeripik = namaKeripik.value,
                            stockKeripik = stockKeripik.value,
                            gambarKeripik = task.result.toString()
                        )

                        myRef.child("keripik").child(namaKeripik.value).setValue(keripik)
                            .addOnCompleteListener{
                                Toast.makeText(context,"Berhasil...", Toast.LENGTH_SHORT).show()
                            }
                            .addOnCanceledListener {
                                Toast.makeText(context,"Gagal coba lagi nanti", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        // Handle failures
                        // ...
                        Log.i(TAG, "error: $task")
                    }
                }
            return
        }

        Log.i(TAG, "error: ${namaKeripik.value}")
        val keripik = Keripik(
            namaKeripik = namaKeripik.value,
            stockKeripik = stockKeripik.value,
            gambarKeripik = gambarKeripik.value
        )

        myRef.child("keripik").child(namaKeripik.value).setValue(keripik)
            .addOnCompleteListener{
                Toast.makeText(context,"Berhasil...", Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(context,"Gagal coba lagi nanti", Toast.LENGTH_SHORT).show()
            }
    }
}