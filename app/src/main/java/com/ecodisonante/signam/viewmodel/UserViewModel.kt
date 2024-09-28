package com.ecodisonante.signam.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ecodisonante.signam.data.UserService
import com.ecodisonante.signam.model.User
import com.ecodisonante.signam.model.UserPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest

class UserViewModel(private val userService: UserService) : ViewModel() {

    private val _user = mutableStateOf(User("", "", "", "", "", ""))
    val user: State<User> = _user

    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> = _showDialog

    private val _dialogTitle = mutableStateOf("")
    val dialogTitle: State<String> = _dialogTitle

    private val _dialogMessage = mutableStateOf("")
    val dialogMessage: State<String> = _dialogMessage

    private val _successAction = mutableStateOf(false)
    val successAction: State<Boolean> = _successAction

    // actualizar usuario cuando se modifica en el formulario
    fun updateUser(
        name: String = _user.value.name,
        email: String = _user.value.email,
        password: String = _user.value.passwd,
        city: String = _user.value.city,
        phone: String = _user.value.phone,
        about: String = _user.value.about,
    ) {
        _user.value = _user.value.copy(
            name = name,
            email = email,
            passwd = password,
            city = city,
            phone = phone,
            about = about
        )
    }

    @Deprecated(
        message = "Este metodo se eliminará cuando se use Firebase Realtime Database con otros objetos",
        ReplaceWith("signInWithEmailAndPassword()")
    )
    fun loginUser(userPreferences: UserPreferences) {
        userService.getByEmail(user.value.email) { login ->
            if (login != null && login.passwd == _user.value.passwd) {
                userPreferences.saveCurrentUser(login)
                _dialogTitle.value = "Bienvenido ${login.name}"
                _dialogMessage.value = ""
                _successAction.value = true
            } else {
                _dialogTitle.value = "Credenciales Incorrectas"
                _dialogMessage.value = "Si no recuerdas tu contraseña puedes intentar recuperarla."
                _successAction.value = false
            }
        }
        _showDialog.value = true
    }

    fun addUserData(onResult: (Boolean?) -> Unit) {
        updateUser(password = "")
        userService.createUser(user.value) { }
    }

    @Deprecated(
        message = "Este metodo se eliminará cuando esté seguro que se usará el otro xd",
        ReplaceWith("sendPasswordResetEmail()")
    )
    fun recoverUser() {
        userService.getByEmail(user.value.email) { recover ->

            val message: String = if (recover != null) {
                "tu clave es ${recover.passwd}"
            } else {
                "usuario no encontrado"
            }

            _dialogTitle.value = "Revisa tu  correo"
            _dialogMessage.value =
                "Hemos enviado un mensaje para que recuperes tu contraseña.\n\n(psst... $message)"
            _successAction.value = true
            _showDialog.value = true
        }
    }


    // Autenticacion usando FirebaseAuth

    fun signInWithEmailAndPassword() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.value.email, user.value.passwd)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    val userDisplayName = FirebaseAuth.getInstance().currentUser?.displayName ?: ""
                    _dialogTitle.value = "Bienvenido $userDisplayName"
                    _dialogMessage.value = ""
                    _successAction.value = true
                } else {
                    Log.e(
                        "UserService",
                        "Error en signInWithEmailAndPassword: ${result.exception?.message}"
                    )
                    _dialogTitle.value = "Credenciales Incorrectas"
                    _dialogMessage.value =
                        "Si no recuerdas tu contraseña, puedes intentar recuperarla."
                    _successAction.value = false
                }
                _showDialog.value = true
            }
    }

    fun registerUserWithEmailPassword() {

        // validar que los datos no estén vacíos
        if (user.value.name.isEmpty() || user.value.email.isEmpty() || user.value.passwd.isEmpty()) {
            _dialogTitle.value = "Faltan Datos"
            _dialogMessage.value = "Debes completar todos los campos para poder registrarte"
            _showDialog.value = true
            return
        }

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(user.value.email, user.value.passwd)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    val firebaseUser = FirebaseAuth.getInstance().currentUser

                    addUserData { }

                    // agrega displayName
                    val profileUpdates =
                        UserProfileChangeRequest.Builder().setDisplayName(user.value.name).build()

                    firebaseUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                _dialogTitle.value = "Bienvenido"
                                _dialogMessage.value =
                                    "Ya puedes acceder con tu correo y contraseña"
                                _successAction.value = true
                            } else {
                                _dialogTitle.value = "Error"
                                _dialogMessage.value =
                                    "No se pudo actualizar el perfil del usuario."
                                _successAction.value = false
                            }
                            _showDialog.value = true
                        }
                } else {
                    // excepcion por email repetido
                    if (result.exception is FirebaseAuthUserCollisionException) {
                        _dialogTitle.value = "Error de registro"
                        _dialogMessage.value = "Ya hay un usuario registrado con ese email."
                    } else {
                        Log.e(
                            "UserService",
                            "Error en registerUserWithEmailPassword: ${result.exception?.message}"
                        )
                        _dialogTitle.value = "Error"
                        _dialogMessage.value = "Lo siento, no pudimos completar tu registro."
                    }
                    _successAction.value = false
                    _showDialog.value = true
                }
            }
    }

    fun sendPasswordResetEmail() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(user.value.email)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    _dialogTitle.value = "Restablecer Contraseña"
                    _dialogMessage.value =
                        "Hemos enviado un mensaje para que recuperes tu contraseña."
                    _successAction.value = true
                } else {
                    Log.e(
                        "UserService",
                        "Error en registerUserWithEmailPassword: ${result.exception?.message}"
                    )
                    _dialogTitle.value = "Error"
                    _dialogMessage.value =
                        "Lo siento, no pudimos enviar el correo de recuperación. Intentalo nuevamente."
                }
                _showDialog.value = true
            }
    }

    fun dismissDialog() {
        _showDialog.value = false
    }
}