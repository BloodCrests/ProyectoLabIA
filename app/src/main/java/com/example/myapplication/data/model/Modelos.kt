package com.example.myapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Pictograma(
    val id: String,
    val texto: String,
    val imagen: String? = null
)

@Serializable
data class Categoria(
    val id: String,
    val nombre: String,
    val pictogramas: List<Pictograma>
)

@Serializable
data class Vocabulario(val categorias: List<Categoria>)