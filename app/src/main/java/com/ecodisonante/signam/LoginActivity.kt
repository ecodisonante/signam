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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
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
import com.ecodisonante.signam.model.DataProvider
import com.ecodisonante.signam.model.UserPreferences
import com.ecodisonante.signam.ui.components.CustomTextField
import com.ecodisonante.signam.ui.components.FatMainButton
import com.ecodisonante.signam.ui.components.MainButton
import com.ecodisonante.signam.ui.theme.SignaMTheme
import com.ecodisonante.signam.ui.theme.lightBG

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LoginDisplay()
        }
    }
}

@Composable
fun LoginDisplay() {
    SignaMTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = lightBG)
                .padding(top = 30.dp)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .width(260.dp)
                    .height(400.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    LoginForm()
                }
            }
        }
    }
}


@Composable
fun LoginForm() {
    val context = LocalContext.current
    val usrPref = UserPreferences(context)
    if (usrPref.getUserList() == null) usrPref.saveUserList(DataProvider.usuarios)

    var emailValue by remember { mutableStateOf("") }
    var passwdValue by remember { mutableStateOf("") }

    CustomTextField(
        value = emailValue,
        label = "Correo",
        onValueChange = { emailValue = it }
    )

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
                text = "Ingresar",
                onClick = {
                    val usuario = usrPref.findUserByEmail(emailValue)

                    if (usuario != null && usuario.passwd == passwdValue) {

                        usrPref.saveCurrentUser(usuario)
                        Toast.makeText(
                            context,
                            "Bienvenido ${usuario.name}",
                            Toast.LENGTH_LONG
                        ).show()

                        context.startActivity(Intent(context, MainActivity::class.java))
                    } else {

                        Toast.makeText(
                            context,
                            "Credenciales Incorrectas",
                            Toast.LENGTH_LONG
                        ).show()

                        context.startActivity(Intent(context, LoginActivity::class.java))
                    }
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
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginDisplay()
}

