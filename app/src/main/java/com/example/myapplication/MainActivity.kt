package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.tts.Hablador
import com.example.myapplication.ui.board.BoardScreen

class MainActivity : ComponentActivity() {
    private lateinit var hablador: Hablador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hablador = Hablador(this)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme() {
                BoardScreen(onHablar = { hablador.decir(it) })
            }
        }
    }

    override fun onDestroy() {
        hablador.liberar()
        super.onDestroy()
    }
}