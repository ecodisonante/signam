package com.ecodisonante.signam.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ecodisonante.signam.data.UserService
import com.ecodisonante.signam.model.User

class UserViewModel(private val userService: UserService) : ViewModel() {

    private val _user = mutableStateOf(User("", "", ""))
    val user: State<User> = _user

    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> = _showDialog

    private val _dialogTitle = mutableStateOf("")
    val dialogTitle: State<String> = _dialogTitle

    private val _dialogMessage = mutableStateOf("")
    val dialogMessage: State<String> = _dialogMessage

    private val _successRegister = mutableStateOf(false)
    val successRegister: State<Boolean> = _successRegister

    fun updateUser(name: String, email: String, password: String) {
        _user.value = user.value.copy(name = name, email = email, passwd = password)
    }

    fun registerUser() {
        if (user.value.name.isEmpty() || user.value.email.isEmpty() || user.value.passwd.isEmpty()) {
            _dialogTitle.value = "Faltan Datos"
            _dialogMessage.value = "Debes completar todos los campos para poder registrarte"
            _showDialog.value = true
            return
        }

        userService.getByEmail(user.value.email) { existingUser ->
            if (existingUser != null) {
                _dialogTitle.value = "Error"
                _dialogMessage.value = "Ya hay un usuario registrado con ese email"
                _showDialog.value = true
            } else {
                userService.createUser(user.value) { success ->
                    if (success) {
                        _dialogTitle.value = "Bienvenido"
                        _dialogMessage.value = "Ya puedes acceder con tu correo y contraseña"
                        _successRegister.value = true
                    } else {
                        _dialogTitle.value = "Error"
                        _dialogMessage.value = "Lo siento, no pudimos completar tu registro."
                    }
                    _showDialog.value = true
                }
            }
        }
    }

    fun dismissDialog() {
        _showDialog.value = false
    }
}
