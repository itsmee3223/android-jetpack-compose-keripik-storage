package com.ramandaaa.keripik_storage.sealed

import com.ramandaaa.keripik_storage.model.Keripik

sealed class DataState {
    class Success(val data: MutableList<Keripik>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading: DataState()
    object Empty: DataState()
}