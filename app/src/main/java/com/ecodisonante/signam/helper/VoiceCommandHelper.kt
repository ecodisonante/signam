package com.ecodisonante.signam.helper


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast

class VoiceRecognitionHelper(private val context: Context) {

    private var speechRecognizer: SpeechRecognizer? = null

    fun startListening(voiceCommands: Map<String, () -> Unit>) {
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    Log.d("VoiceRecognitionHelper", "Escuchando...")
                }

                override fun onError(error: Int) {
                    Log.e("VoiceRecognitionHelper", "Error: $error")
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    matches?.let {
                        handleVoiceCommands(it, voiceCommands)
                    }
                }

                override fun onPartialResults(p0: Bundle?) {}
                override fun onEvent(p0: Int, p1: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(p0: Float) {}
                override fun onBufferReceived(p0: ByteArray?) {}
                override fun onEndOfSpeech() {}
            })
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
        }
        speechRecognizer?.startListening(intent)
    }

    private fun handleVoiceCommands(result: List<String>, commands: Map<String, () -> Unit>) {
        for (command in result) {
            val action = commands[command.lowercase()]
            if (action != null) {
                action()
                return
            }
        }

        // no se reconoce el mensaje ...
        Log.d("VoiceRecognitionHelper", "Comando no reconocido: ${result.firstOrNull()}")
        Toast.makeText(context, "No te entendí. Prueba decir 'ayuda'", Toast.LENGTH_SHORT).show()
    }
}

