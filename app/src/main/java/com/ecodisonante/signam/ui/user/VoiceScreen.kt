package com.ecodisonante.signam.ui.user

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.ecodisonante.signam.MainActivity
import com.ecodisonante.signam.RecoveryActivity
import com.ecodisonante.signam.ui.components.CustomAlertInfo
import com.ecodisonante.signam.ui.components.CustomCard
import com.ecodisonante.signam.ui.components.CustomTextField
import com.ecodisonante.signam.ui.components.FatMainButton
import com.ecodisonante.signam.ui.components.MainButton
import com.ecodisonante.signam.ui.theme.lightBG
import com.ecodisonante.signam.viewmodel.UserViewModel

@Preview
@Composable
fun VoiceScreen() {
    val context = LocalContext.current


    val speech = remember { SpeechRecognizer.createSpeechRecognizer((context)) }
    var text by remember { mutableStateOf("") }

    val recognizer = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
        }
    }

    val permission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) speech.startListening(recognizer)
        else Toast.makeText(context, "permiso del microfono denegado", Toast.LENGTH_SHORT).show()
    }

    val listener = object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {
            text = "Escuchando..."
        }

        override fun onEndOfSpeech() {
            text = "Procesando..."
        }

        override fun onError(p0: Int) {
            text = "Oops, no te entendi nada"
        }

        override fun onResults(p0: Bundle?) {
            val matches = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            text = matches?.firstOrNull() ?: "No se pudo reconocer"
        }

        override fun onPartialResults(p0: Bundle?) {}
        override fun onEvent(p0: Int, p1: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(p0: Float) {}
        override fun onBufferReceived(p0: ByteArray?) {}
    }

    speech.setRecognitionListener(listener)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = lightBG)
            .padding(top = 30.dp)
            .fillMaxSize()
    ) {
        CustomCard(customHeight = 450) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    shape = RoundedCornerShape(10.dp),
                    value = text,
                    label = { Text("Presiona para hablar") },
                    onValueChange = { },
                    singleLine = false,
                    minLines = 6,
                    readOnly = true
                )

                Spacer(modifier = Modifier.size(50.dp))

                FatMainButton(
                    text = "Transcribir",
                    onClick = { permission.launch(Manifest.permission.RECORD_AUDIO) })

                Spacer(modifier = Modifier.size(30.dp))

                MainButton(
                    text = "Volver",
                    onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    },
                )

            }
//                Text(
//                    text = text,
//                    style = MaterialTheme.typography.headlineMedium,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )

//            Button(onClick = { permission.launch(Manifest.permission.RECORD_AUDIO) }) {
//                Text(text = "Presionar y hablar")
//            }
        }
    }
}
