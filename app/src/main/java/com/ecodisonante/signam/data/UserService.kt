package com.ecodisonante.signam.data

import com.ecodisonante.signam.model.User

interface UserService {
    fun getByEmail(email: String, onResult: (User?) -> Unit)
    fun createUser(user: User, onResult: (Boolean) -> Unit)
}
