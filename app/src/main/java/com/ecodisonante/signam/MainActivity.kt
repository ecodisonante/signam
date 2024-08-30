package com.ecodisonante.signam

import android.content.Intent
import android.os.Bundle
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
import com.ecodisonante.signam.ui.components.FatMainButton
import com.ecodisonante.signam.ui.components.MainButton
import com.ecodisonante.signam.ui.theme.SignaMTheme
import com.ecodisonante.signam.ui.theme.lightBG

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SignaMTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(color = lightBG)
                ) {
                    DisplayMainLogo()
                    DisplayMainButtons()
                }
            }
        }
    }

}

data class Mensaje(val nombre: String, val desc: String)


@Composable
fun DisplayMainLogo() {
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_adaptive_fore),
        contentDescription = "Logo principal",
        modifier = Modifier
            .size(250.dp)
            .padding(top = 50.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.title),
        contentDescription = "Logo principal",
        modifier = Modifier
            .width(250.dp)
    )
}


@Composable
fun DisplayMainButtons() {
    val context = LocalContext.current

    Row {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .width(250.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FatMainButton(
                text = "Ingresar",
                onClick = { context.startActivity(Intent(context, LoginActivity::class.java)) },
            )

            Spacer(modifier = Modifier.size(50.dp))

            MainButton(
                text = "Registrarse",
                onClick = { context.startActivity(Intent(context, RegisterActivity::class.java)) },
            )

            Spacer(modifier = Modifier.size(100.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Previsualiza() {
    SignaMTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(color = lightBG)
        ) {
            DisplayMainLogo()
            DisplayMainButtons()
        }
    }
}

