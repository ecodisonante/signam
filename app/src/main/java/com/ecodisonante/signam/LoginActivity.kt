package com.ecodisonante.signam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
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
import com.ecodisonante.signam.ui.components.CustomTextField
import com.ecodisonante.signam.ui.components.MainButton
import com.ecodisonante.signam.ui.theme.SignaMTheme
import com.ecodisonante.signam.ui.theme.lightBG

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SignaMTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(color = lightBG)
                        .fillMaxSize()
                ) {
                    LoginForm()
                }
            }
        }
    }
}

@Composable
fun LoginForm() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .width(260.dp)
                .height(350.dp)
        ) {
            Column(

                Modifier.padding(20.dp)
            ) {
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

                Spacer(modifier = Modifier.size(15.dp))

                MainButton(
                    text = "Ingresar",
                    onClick = {
                        context.startActivity(Intent(context, LoginActivity::class.java))
                    },
                )
            }

        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SignaMTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(color = lightBG)
        ) {
            LoginForm()
        }
    }
}