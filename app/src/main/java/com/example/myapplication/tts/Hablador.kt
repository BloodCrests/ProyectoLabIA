package com.example.myapplication.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class Hablador(context: Context) : TextToSpeech.OnInitListener {
    private val tts = TextToSpeech(context, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("es", "MX")
        }
    }

    fun decir(frase: String) {
        tts.speak(frase, TextToSpeech.QUEUE_FLUSH, null, "frase")
    }

    fun liberar() {
        tts.shutdown()
    }
}