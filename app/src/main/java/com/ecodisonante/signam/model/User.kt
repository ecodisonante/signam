package com.ecodisonante.signam.model

import android.util.Base64

/**
 * Clase modelo de Usuario
 */
data class User(
    val email: String = "",
    val name: String = "",
    val passwd: String = "",
    val city: String = "",
    val phone: String = "",
    val about: String = "",
)

