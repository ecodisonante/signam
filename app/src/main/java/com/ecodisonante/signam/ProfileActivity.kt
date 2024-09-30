package com.ecodisonante.signam

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ecodisonante.signam.data.FirebaseUserService
import com.ecodisonante.signam.data.UserService
import com.ecodisonante.signam.model.User
import com.ecodisonante.signam.ui.user.ProfileScreen
import com.ecodisonante.signam.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : ComponentActivity() {

    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Comprobar que la sesion esta activa
        userService = FirebaseUserService(FirebaseDatabase.getInstance().reference)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) startActivity(Intent(this, MainActivity::class.java))

        prepareUserProfile(currentUser!!) { user ->
            setContent {
                val viewModel = UserViewModel(userService, user)
                ProfileScreen(viewModel)
            }
        }
    }

    private fun prepareUserProfile(currentUser: FirebaseUser, onResult: (User) -> Unit) {
        val email = currentUser.email ?: ""
        val displayName = currentUser.displayName ?: ""

        userService.getByEmail(email) { existingUser ->
            // Si no existe se crea uno nuevo
            if (existingUser == null) {
                val nuevo = User(email = email, name = displayName)
                userService.createUser(nuevo) {
                    onResult(nuevo)
                }
            } else {
                // Retorna datos guardados
                onResult(existingUser)
            }
        }
    }
}