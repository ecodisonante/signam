package com.ecodisonante.signam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import com.ecodisonante.signam.model.User
import com.ecodisonante.signam.model.UserDataProvider
import com.ecodisonante.signam.model.UserPreferences
import com.ecodisonante.signam.ui.components.CustomAlertInfo
import com.ecodisonante.signam.ui.components.CustomCard
import com.ecodisonante.signam.ui.components.CustomTextField
import com.ecodisonante.signam.ui.components.FatMainButton
import com.ecodisonante.signam.ui.components.MainButton
import com.ecodisonante.signam.ui.theme.SignaMTheme
import com.ecodisonante.signam.ui.theme.lightBG

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterDisplay()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterDisplay() {
    SignaMTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = lightBG)
                .padding(top = 30.dp)
                .fillMaxSize()
        ) {
            CustomCard(customHeight = 500) { RegisterForm() }
        }
    }
}

@Composable
fun RegisterForm() {
    val context = LocalContext.current
    val usrPref = UserPreferences(context)
    if (usrPref.getUserList() == null) usrPref.saveUserList(UserDataProvider.usuarios)

    var nameValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var passwdValue by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var successRegister by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    CustomTextField(value = nameValue, label = "Nombre", onValueChange = { nameValue = it })

    Spacer(modifier = Modifier.size(15.dp))

    CustomTextField(value = emailValue, label = "Correo", onValueChange = { emailValue = it })

    Spacer(modifier = Modifier.size(15.dp))

    CustomTextField(
        value = passwdValue,
        label = "Contraseña",
        onValueChange = { passwdValue = it },
        isPassword = true
    )

    Row {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .width(250.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.size(15.dp))

            FatMainButton(
                text = "Registrarse",
                onClick = {
                    val existe = usrPref.findUserByEmail(emailValue)

                    if (existe != null) {
                        dialogTitle = "Error"
                        dialogMessage = "Ya hay un usuario registrado con ese email"
                    } else {
                        val nuevo = User(name = nameValue, email = emailValue, passwd = passwdValue)
                        usrPref.addUserToList(nuevo)

                        dialogTitle = "Bienvenido"
                        dialogMessage = "Ya puedes acceder con tu correo y contraseña"
                        successRegister = true
                    }

                    showDialog = true
                },
            )

            Spacer(modifier = Modifier.size(15.dp))

            MainButton(
                text = "Volver",
                onClick = {
                    context.startActivity(Intent(context, MainActivity::class.java))
                },
            )
        }
    }

    CustomAlertInfo(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        title = dialogTitle,
        message = dialogMessage,
        onConfirm = {
            if (successRegister) context.startActivity(Intent(context, MainActivity::class.java))
            showDialog = false
        },
    )
}

