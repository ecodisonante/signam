package com.ecodisonante.signam.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecodisonante.signam.ui.components.CustomAlertInfo
import com.ecodisonante.signam.ui.theme.SignaMTheme
import com.ecodisonante.signam.ui.theme.lightBG

class SelectGameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting()
        }
    }
}

@Preview
@Composable
fun Greeting() {
    var showDialog by remember { mutableStateOf(false) }

    SignaMTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = lightBG),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.size(150.dp), onClick = { showDialog = true }) {
                Text(text = "Frases", fontSize = 30.sp)
            }

            Spacer(modifier = Modifier.size(70.dp))

            Button(modifier = Modifier.size(150.dp), onClick = { showDialog = true }) {
                Text(text = "Letras", fontSize = 30.sp)
            }
        }
    }

    CustomAlertInfo(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = { showDialog = false },
        title = "Pronto",
        message = "estamos trabajando para ud...",
    )
}

