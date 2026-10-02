package com.example.myapplication.ui.board

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.model.Categoria
import com.example.myapplication.data.model.Pictograma
import com.example.myapplication.data.repository.VocabularioRepository

class BoardViewModel(app: Application) : AndroidViewModel(app) {

    val categorias: List<Categoria> = VocabularioRepository(app).cargar()

    var categoriaActual by mutableStateOf(categorias.firstOrNull()?.id.orEmpty())
        private set

    val seleccion = mutableStateListOf<Pictograma>()

    val pictogramasVisibles: List<Pictograma>
        get() = categorias.firstOrNull { it.id == categoriaActual }?.pictogramas.orEmpty()

    val frase: String
        get() = seleccion.joinToString(" ") { it.texto }
            .replaceFirstChar { it.uppercase() }

    fun elegirCategoria(id: String) { categoriaActual = id }
    fun agregar(p: Pictograma) { seleccion.add(p) }
    fun borrarUltimo() { if (seleccion.isNotEmpty()) seleccion.removeAt(seleccion.lastIndex) }
    fun limpiar() { seleccion.clear() }
}