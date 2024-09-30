package com.ecodisonante.signam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ecodisonante.signam.data.FirebaseUserService
import com.ecodisonante.signam.data.UserService
import com.ecodisonante.signam.ui.user.RecoverScreen
import com.ecodisonante.signam.viewmodel.UserViewModel
import com.google.firebase.database.FirebaseDatabase

class RecoveryActivity : ComponentActivity() {

    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar FirebaseUserService u otra implementación
        userService = FirebaseUserService(FirebaseDatabase.getInstance().reference)
        setContent {
            val viewModel = UserViewModel(userService)
            RecoverScreen(viewModel)
        }
    }
}