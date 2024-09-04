package com.ecodisonante.signam.model


/**
 * Clase modelo de Usuario
 */
data class User(
    val email: String,
    val name: String,
    val passwd: String,
)


class UserDataProvider {
    companion object {
        /**
         * Lista de usuarios de prueba
         */
        val usuarios = mutableListOf(
            User("test@duocuc.cl", "Usuario Test", "12345"),
            User("francisco@duocuc.cl", "Francisco Valdés", "12345"),
            User("admin@duocuc.cl", "Usuario Admin", "12345"),
        )

    }
}