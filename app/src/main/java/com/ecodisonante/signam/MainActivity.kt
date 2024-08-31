package com.ecodisonante.signam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecodisonante.signam.model.UserPreferences
import com.ecodisonante.signam.ui.components.FatMainButton
import com.ecodisonante.signam.ui.components.MainButton
import com.ecodisonante.signam.ui.theme.SignaMTheme
import com.ecodisonante.signam.ui.theme.lightBG

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MainDisplay()
        }
    }
}

@Composable
fun MainDisplay() {
    SignaMTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = lightBG)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_adaptive_fore),
                contentDescription = "Logo principal",
                modifier = Modifier
                    .padding(top = 50.dp)
                    .size(250.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.title),
                contentDescription = "Logo principal",
                modifier = Modifier.width(250.dp)
            )

            Row {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .width(250.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LoginButtons()
                }
            }
        }
    }
}


@Composable
fun LoginButtons() {
    val context = LocalContext.current
    val usrPref = UserPreferences(context)
    val currentUser = usrPref.getCurrentUser()

    FatMainButton(
        text = if (currentUser != null) "Hola ${currentUser.name}" else "Ingresar",
        onClick = {
            // TODO: cual es la proxima pagina ?? xd
            if (currentUser == null) {
                context.startActivity(Intent(context, LoginActivity::class.java))
            } else {
                Toast.makeText(
                    context,
                    "Ya entraste wn!! que mas quieres??",
                    Toast.LENGTH_LONG
                ).show()
            }

        },
    )

    Spacer(modifier = Modifier.size(50.dp))

    MainButton(
        text = if (currentUser != null) "cambiar usuario" else "Registrarse",
        onClick = {
            if (currentUser == null) {
                context.startActivity(Intent(context, RegisterActivity::class.java))
            } else {
                val prefs = UserPreferences(context)
                prefs.saveCurrentUser(null)
                context.startActivity(Intent(context, MainActivity::class.java))
            }
        },
    )

    Spacer(modifier = Modifier.size(100.dp))

}

@Preview(showBackground = true)
@Composable
fun Previsualiza() {
    MainDisplay()
}

