package com.ecodisonante.signam

import android.app.Activity
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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()

        setContent {
            MainDisplay()
        }
    }
}

@Preview(showBackground = true)
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
    val currentUser = FirebaseAuth.getInstance().currentUser

    FatMainButton(
        text = if (currentUser != null) "Mi Perfil" else "Ingresar",
        onClick = {
            if (currentUser == null) {
                context.startActivity(Intent(context, LoginActivity::class.java))
            } else {
                context.startActivity(Intent(context, ProfileActivity::class.java))
            }
        },
    )


    if (currentUser != null) {
        Spacer(modifier = Modifier.size(20.dp))

        MainButton(
            text = "Transcribir",
            onClick = { context.startActivity(Intent(context, VoiceActivity::class.java)) },
        )

//        Spacer(modifier = Modifier.size(20.dp))
//
//        MainButton(
//            text = "Localizar",
//            onClick = { },
//        )
    }


    Spacer(modifier = Modifier.size(50.dp))

    MainButton(
        text = if (currentUser != null) "Salir" else "Registrarse",
        onClick = {
            if (currentUser == null) {
                context.startActivity(Intent(context, RegisterActivity::class.java))
            } else {
                FirebaseAuth.getInstance().signOut()
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as Activity).finish()
            }
        },
    )
//
//    if (currentUser != null) {
//        Spacer(modifier = Modifier.size(20.dp))
//        Text(text = currentUser.displayName!!)
//    }
    Spacer(modifier = Modifier.size(70.dp))

}

