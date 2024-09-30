package com.ecodisonante.signam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ecodisonante.signam.data.FirebaseUserService
import com.ecodisonante.signam.data.UserService
import com.ecodisonante.signam.ui.user.LoginScreen
import com.ecodisonante.signam.viewmodel.UserViewModel
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : ComponentActivity() {

    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar FirebaseUserService u otra implementación
        userService = FirebaseUserService(FirebaseDatabase.getInstance().reference)
        setContent {
            val viewModel = UserViewModel(userService)
            LoginScreen(viewModel)
        }
    }
}

