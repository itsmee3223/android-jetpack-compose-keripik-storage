package com.ramandaaa.keripik_storage.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramandaaa.keripik_storage.sealed.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginRegisterActivity"
@HiltViewModel
class LoginRegisterViewModel @Inject constructor(): ViewModel() {
    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _error = mutableStateOf("")
    private val error: State<String> = _error

    private val _userEmail = mutableStateOf("")
    val userEmail: State<String> = _userEmail

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _confirmPassword = mutableStateOf("")
    val confirmpassword: State<String> = _confirmPassword

    fun setUserEmail(email: String) {
        _userEmail.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    private fun setError(error: String) {
        _error.value = error
    }

    init {
        _isLoggedIn.value = getCurrentUser() != null
    }

    fun createUserWithEmailAndPassword(context: Context, navController: NavHostController) = viewModelScope.launch {
        _error.value = ""
        if(isConfirmPassword()) {
            Firebase.auth.createUserWithEmailAndPassword(userEmail.value, password.value)
                .addOnCompleteListener { task -> signInCompletedTask(task, context, navController) }
        } else {
            Toast.makeText(context, "Password tidak sama!!!", Toast.LENGTH_SHORT).show()
        }
    }

    fun signInWithEmailAndPassword(context: Context, navController: NavHostController) = viewModelScope.launch {
        try {
            setError("")
            Firebase.auth.signInWithEmailAndPassword(userEmail.value, password.value)
                .addOnCompleteListener { task -> signInCompletedTask(task, context, navController) }
        } catch (e: Exception) {
            setError(e.localizedMessage ?: "Unknown error")
            Log.d(TAG, "Sign in fail: $e")
        }
    }

    private fun signInCompletedTask(task: Task<AuthResult>, context: Context, navController: NavHostController) {
        if (task.isSuccessful) {
            Log.d(TAG, "SignInWithEmail:success")
            _userEmail.value = ""
            _password.value = ""

            Toast.makeText(context, "Selamat Datang...", Toast.LENGTH_SHORT).show()

            navController.popBackStack(Screens.LoginScreen.route, inclusive = true)
            navController.popBackStack(Screens.RegisterScreen.route, inclusive = true)

            navController.navigate(Screens.HomeScreen.route)
        } else {
            setError(task.exception?.localizedMessage ?: "Unknown error")
            // If sign in fails, display a message to the user.
            Toast.makeText(context, error.value, Toast.LENGTH_SHORT).show()
            Log.w(TAG, "SignInWithEmail:failure", task.exception)
        }
        viewModelScope.launch {
            _isLoggedIn.value = getCurrentUser() != null
        }
    }

    private fun getCurrentUser() : FirebaseUser? {
        val user = Firebase.auth.currentUser
        Log.d(TAG, "user display name: ${user?.displayName}, email: ${user?.email}")
        return user
    }

    fun isValidEmailAndPassword() : Boolean {
        if (userEmail.value.isBlank() || password.value.isBlank()) {
            return false
        }
        return true
    }

    private fun isConfirmPassword() : Boolean {
        if(password.value != confirmpassword.value) {
            return false
        }
        return true
    }

    fun signOut() = viewModelScope.launch {
        Firebase.auth.signOut()
        _isLoggedIn.value = false
    }
}