package com.ecodisonante.signam

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecodisonante.signam.data.FirebaseUserService
import com.ecodisonante.signam.model.User
import com.ecodisonante.signam.model.UserDataProvider
import com.ecodisonante.signam.model.UserPreferences
import com.ecodisonante.signam.data.UserService
import com.ecodisonante.signam.ui.components.CustomAlertInfo
import com.ecodisonante.signam.ui.components.CustomCard
import com.ecodisonante.signam.ui.components.CustomTextField
import com.ecodisonante.signam.ui.components.FatMainButton
import com.ecodisonante.signam.ui.components.MainButton
import com.ecodisonante.signam.ui.theme.SignaMTheme
import com.ecodisonante.signam.ui.theme.lightBG
import com.ecodisonante.signam.ui.user.RegisterScreen
import com.ecodisonante.signam.viewmodel.UserViewModel
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : ComponentActivity() {

    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar FirebaseUserService u otra implementación
        userService = FirebaseUserService(FirebaseDatabase.getInstance().reference)
        setContent {
            val viewModel = UserViewModel(userService)
            RegisterScreen(viewModel)
        }
    }
}
