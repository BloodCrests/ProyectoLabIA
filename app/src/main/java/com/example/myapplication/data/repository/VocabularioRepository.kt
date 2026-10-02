package com.example.myapplication.data.repository

import android.content.Context
import com.example.myapplication.data.model.Categoria
import com.example.myapplication.data.model.Vocabulario
import kotlinx.serialization.json.Json

class VocabularioRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    fun cargar(): List<Categoria> =
        context.assets.open("vocabulario.json")
            .bufferedReader()
            .use { json.decodeFromString<Vocabulario>(it.readText()).categorias }
}